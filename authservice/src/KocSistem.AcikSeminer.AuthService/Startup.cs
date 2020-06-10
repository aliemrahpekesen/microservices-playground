using KocSistem.AcikSeminer.AuthService.Common;
using KocSistem.AcikSeminer.AuthService.Extension;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Steeltoe.Discovery.Client;
using Steeltoe.Management.Endpoint.Health;
using Steeltoe.Management.Endpoint.HeapDump;
using Steeltoe.Management.Endpoint.Hypermedia;
using Steeltoe.Management.Endpoint.Info;
using Steeltoe.Management.Endpoint.Mappings;
using Steeltoe.Management.Endpoint.Metrics;
using Steeltoe.Management.Endpoint.Trace;
using Steeltoe.Management.Exporter.Tracing;

namespace KocSistem.AcikSeminer.AuthService
{
    public class Startup
    {
        public Startup(IConfiguration configuration, IWebHostEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .AddJsonFile($"appsettings.{env.EnvironmentName}.json", optional: true)
                .AddEnvironmentVariables();

            Configuration = builder.Build();
        }

        public IConfiguration Configuration { get; }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env, IHostApplicationLifetime lifetime, ILoggerFactory loggerFactory)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseRouting();

            app.UseAuthorization();

            app.UserZipkinCore(lifetime, loggerFactory, $"{Configuration["Tracking:Zipkin:Host"]}:{Configuration["Tracking:Zipkin:Port"]}", Configuration["spring:application:name"]);

            app.UseDiscoveryClient();

            app.UseHealthActuator();

            app.UseInfoActuator();

            app.UseHypermediaActuator();

            app.UseMetricsActuator();

            app.UseTraceActuator();

            app.UseHeapDumpActuator();

            app.UseMappingsActuator();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }

        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllers();

            services.AddStackExchangeRedisCache(options =>
            {
                options.Configuration = $"{Configuration.GetValue<string>("Caching:Host")}:{Configuration.GetValue<string>("Caching:Port")}";
                options.InstanceName = Configuration.GetValue<string>("Caching:InstanceName");
            });

            services.AddDiscoveryClient(Configuration);
            services.AddZipkinExporter(Configuration);
            services.AddHealthActuator(Configuration);
            services.AddInfoActuator(Configuration);
            services.AddHypermediaActuator(Configuration);
            services.AddMetricsActuator(Configuration);
            services.AddTraceActuator(Configuration);
            services.AddHeapDumpActuator(Configuration);
            services.AddMappingsActuator(Configuration);
        }
    }
}
