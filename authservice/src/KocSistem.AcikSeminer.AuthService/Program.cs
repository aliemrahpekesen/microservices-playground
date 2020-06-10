using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using NLog.Web;
using Steeltoe.Extensions.Logging;

namespace KocSistem.AcikSeminer.AuthService
{
    public class Program
    {
        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>()
                        .UseNLog()
                    .ConfigureLogging((builderContext, loggingBuilder) =>
                    {
                        //loggingBuilder.ClearProviders();
                        loggingBuilder.AddConfiguration(builderContext.Configuration.GetSection("Logging"));
                        loggingBuilder.AddDynamicConsole();
                    });
                    ;
                });

        public static void Main(string[] args)
        {
            CreateHostBuilder(args).Build().Run();
        }
    }
}
