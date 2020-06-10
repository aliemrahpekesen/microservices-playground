using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using zipkin4net;
using zipkin4net.Middleware;
using zipkin4net.Tracers.Zipkin;
using zipkin4net.Transport.Http;

namespace KocSistem.AcikSeminer.AuthService.Extension
{
    public static class ZipkinExtension
    {
        public static void UserZipkinCore(this IApplicationBuilder app, IHostApplicationLifetime applicationLifetime, ILoggerFactory loggerFactory, string zipKinControllerUrl, string serviceName)
        {
            applicationLifetime.ApplicationStarted.Register(() =>
            {
                TraceManager.SamplingRate = 1.0f;
                var logger = new TracingLogger(loggerFactory, "zipkin4net");
                var httpSender = new HttpZipkinSender(zipKinControllerUrl, "application/json");
                var tracer = new ZipkinTracer(httpSender, new JSONSpanSerializer());
                TraceManager.RegisterTracer(tracer);
                TraceManager.Start(logger);
            });
            applicationLifetime.ApplicationStopped.Register(() => TraceManager.Stop());
            app.UseTracing(serviceName);
        }
    }
}
