$ErrorActionPreference = 'Stop'
$driver = Join-Path $env:USERPROFILE '.m2\repository\com\mysql\mysql-connector-j\9.4.0\mysql-connector-j-9.4.0.jar'
if (-not (Test-Path $driver)) {
    Write-Error "MySQL JDBC driver not found at $driver"
}
if (-not (Test-Path 'out')) {
    New-Item -ItemType Directory -Path 'out' | Out-Null
}
javac -d out (Get-ChildItem -LiteralPath src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp "out;$driver" Main