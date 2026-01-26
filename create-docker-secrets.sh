secrets=(
    "DB_NAME:0"
    "DB_USER_NAME:0"
    "DB_PASSWORD:1"
    "ASAAS_SANDBOX_API_KEY:1"
    "ASAAS_WEBHOOK_TOKEN:1"
    "USER_MAIL:0"
)

# Cores para o terminal
YELLOW='\033[1;33m'
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # Sem cor

echo -e "${YELLOW}=== Criador de Docker Secrets (Bash - SEM newline invisivel) ===${NC}"

for item in "${secrets[@]}"; do
    IFS=":" read -r NAME SECURE <<< "$item"

    if [ "$SECURE" -eq 1 ]; then
        echo -n "Informe o valor para $NAME (entrada oculta): "
        read -s PLAIN_VALUE
        echo "" # Pular linha após o read -s
    else
        read -p "Informe o valor para $NAME: " PLAIN_VALUE
    fi

    # Remover espaços em branco no início/fim (Trim) para evitar caracteres fantasmas no valor do secret
    PLAIN_VALUE=$(echo "$PLAIN_VALUE" | xargs)

    if [ -z "$PLAIN_VALUE" ]; then
        echo -e "${RED}Erro: Valor para $NAME não pode ser vazio.${NC}"
        echo "--------------------------------------"
        continue
    fi

    # Criar arquivo temporário seguro
    TEMP_FILE=$(mktemp)
    
    # Escrever o valor SEM newline e SEM BOM 
    printf "%s" "$PLAIN_VALUE" > "$TEMP_FILE"

    # Tenta remover o secret se já existir
    docker secret rm "$NAME" 2>/dev/null >/dev/null

    # Cria o novo secret
    if docker secret create "$NAME" "$TEMP_FILE" >/dev/null 2>&1; then
        echo -e "${GREEN}Secret '$NAME' criado com sucesso.${NC}"
    else
        echo -e "${RED}Erro ao criar o secret '$NAME'.${NC}"
    fi

    # Limpar arquivo temporário
    rm -f "$TEMP_FILE"
    echo "--------------------------------------"
done

echo -e "${GREEN}Processo finalizado.${NC}"
