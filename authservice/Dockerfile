# Stage 1 - get production environment
FROM mcr.microsoft.com/dotnet/core/aspnet:3.1 AS base
EXPOSE 80
EXPOSE 8082

# Stage 2 - get build environment & build
FROM mcr.microsoft.com/dotnet/core/sdk:3.1 AS build
WORKDIR /src
COPY ["./", "./"]
RUN dotnet restore "src/KocSistem.AcikSeminer.AuthService/KocSistem.AcikSeminer.AuthService.csproj"
RUN dotnet build "src/KocSistem.AcikSeminer.AuthService/KocSistem.AcikSeminer.AuthService.csproj" -c Release -o /app


# Stage 3 - application publish on build enviroment
FROM build AS publish
RUN dotnet publish "src/KocSistem.AcikSeminer.AuthService/KocSistem.AcikSeminer.AuthService.csproj" -c Release -o /app/publish

# Stage 4 - copy files in production enviroment
FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENV ASPNETCORE_ENVIRONMENT=docker
ENTRYPOINT ["dotnet", "KocSistem.AcikSeminer.AuthService.dll"]