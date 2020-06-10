using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;

namespace KocSistem.AcikSeminer.AuthService.Common
{
    public class Utils
    {
        /// <summary>
        /// Get local ip
        /// </summary>
        /// <returns></returns>
        public static string LocalNetWorkIp()
        {
            return NetworkInterface
                .GetAllNetworkInterfaces()
                .Select(p => p.GetIPProperties())
                .SelectMany(p => p.UnicastAddresses)
                .FirstOrDefault(p => p.Address.AddressFamily == AddressFamily.InterNetwork && !IPAddress.IsLoopback(p.Address))?.Address.ToString() ?? "0.0.0.0";
        }
    }
}
