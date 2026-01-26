$secrets = @(
    @{ Name = "DB_NAME"; Secure = $false },
    @{ Name = "DB_USER_NAME"; Secure = $false },
    @{ Name = "DB_PASSWORD"; Secure = $true },
    @{ Name = "ASAAS_SANDBOX_API_KEY"; Secure = $true },
    @{ Name = "ASAAS_WEBHOOK_TOKEN"; Secure = $true },
    @{ Name = "USER_MAIL"; Secure = $false }
)

Write-Host "=== Criador de Docker Secrets (SEM newline invisivel) ===" -ForegroundColor Yellow

foreach ($secret in $secrets) {
    try {
        if ($secret.Secure) {
            $value = Read-Host "Informe o valor para $($secret.Name)" -AsSecureString
            $plainValue = [Runtime.InteropServices.Marshal]::PtrToStringAuto(
                [Runtime.InteropServices.Marshal]::SecureStringToBSTR($value)
            )
        } else {
            $plainValue = Read-Host "Informe o valor para $($secret.Name)"
        }

        if ([string]::IsNullOrWhiteSpace($plainValue)) {
            throw "Valor não pode ser vazio."
        }

        $tempFile = [System.IO.Path]::GetTempFileName()
        $utf8WithoutBom = New-Object System.Text.UTF8Encoding($false)
        [System.IO.File]::WriteAllText($tempFile, $plainValue.Trim(), $utf8WithoutBom)

        docker secret rm $($secret.Name) 2>$null | Out-Null

        docker secret create $($secret.Name) $tempFile | Out-Null

        Remove-Item $tempFile -Force

        if ($LASTEXITCODE -eq 0) {
            Write-Host "Secret '$($secret.Name)' criado com sucesso." -ForegroundColor Green
        } else {
            throw "Erro ao criar o secret '$($secret.Name)'."
        }
    } catch {
        Write-Host "Erro ao criar o secret '$($secret.Name)'." -ForegroundColor Red
        Write-Host "Motivo: $($_.Exception.Message)" -ForegroundColor Yellow
    }

    Write-Host "--------------------------------------"
}

Write-Host "Processo finalizado." -ForegroundColor Green