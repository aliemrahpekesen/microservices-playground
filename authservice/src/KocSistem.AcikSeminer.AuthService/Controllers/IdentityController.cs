using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.Logging;
using Microsoft.IdentityModel.Tokens;

namespace KocSistem.AcikSeminer.AuthService.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class IdentityController : ControllerBase
    {
        private readonly IDistributedCache _cache;
        private readonly ILogger<IdentityController> _logger;
        private string myAudience = "http://kocsistem.com.tr";
        private string myIssuer = "KocSistem";
        private string mySecret = "303e35cd-0428-409e-9710-8baca440f7ab";

        public IdentityController(ILogger<IdentityController> logger, IDistributedCache cache)
        {
            _logger = logger;
            _cache = cache;
        }

        [HttpGet("check")]
        public ActionResult Check([FromHeader] string token)
        {
            var result = ValidateCurrentToken(token);
            _logger.LogInformation($"token is {(result ? "valid" : "invalid")}");

            return this.Ok(result);
        }

        [HttpPost("login")]
        public ActionResult Login(LoginModel model)
        {
            var userList = GetUserList();

            if (userList != null && userList.ContainsKey(model.Username) && userList[model.Username] == model.Password)
            {
                var claimList = new List<Claim>
                {
                    new Claim("roles", "admin"),
                    new Claim(ClaimTypes.Email, model.Username),
                    new Claim(ClaimTypes.GivenName, "Jane"),
                    new Claim(ClaimTypes.Surname, "Doe")
                };
                _logger.LogInformation($"'{model.Username}' > user authenticated.");
                return this.Ok(new IdentityModel { Token = GenerateToken(claimList) });
            }
            else
            {
                _logger.LogInformation($"'{model.Username}' > unauthorized user.");
                return this.BadRequest("Invalid username or password");
            }
        }

        private string GenerateToken(List<Claim> claims)
        {
            var mySecurityKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(mySecret));

            var tokenHandler = new JwtSecurityTokenHandler();
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(claims),
                Expires = DateTime.UtcNow.AddDays(7),
                Issuer = myIssuer,
                Audience = myAudience,
                SigningCredentials = new SigningCredentials(mySecurityKey, SecurityAlgorithms.HmacSha256Signature)
            };

            var token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);
        }

        private Dictionary<string, string> GetUserList()
        {
            var userListBytes = _cache.Get("userList");
            var mStream = new MemoryStream();
            var binFormatter = new BinaryFormatter();
            if (userListBytes == null)
            {
                var userListDb = new Dictionary<string, string>
                {
                    {"admin@kocsistem.com.tr", "123456"}
                };

                binFormatter.Serialize(mStream, userListDb);
                userListBytes = mStream.ToArray();
                _cache.Set("userList", userListBytes);
                _logger.LogInformation($"user list written to redis");
            }

            mStream.Write(userListBytes, 0, userListBytes.Length);
            mStream.Position = 0;
            var userList = binFormatter.Deserialize(mStream) as Dictionary<string, string>;
            return userList;
        }

        private bool ValidateCurrentToken(string token)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var mySecurityKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(mySecret));

            try
            {
                tokenHandler.ValidateToken(token, new TokenValidationParameters
                {
                    ValidateIssuerSigningKey = true,
                    ValidateIssuer = true,
                    ValidateAudience = true,
                    ValidIssuer = myIssuer,
                    ValidAudience = myAudience,
                    IssuerSigningKey = mySecurityKey
                }, out SecurityToken validatedToken);
            }
            catch (Exception ex)
            {
                return false;
            }
            return true;
        }
    }
}
