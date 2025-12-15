# Getting Started

OBS: Necessário instalar o Docker(Linux) ou Docker Setup(Windows)



### Primeiro passo:

```bash
git clone https://github.com/DAC-ProjetoFinal-2025/Charge-System
```

### Segundo passo:

```bash
cd Charge-System
OBS: Utilizar a branch dev, usando o comando git checkout dev no terminal
```

### Terceiro passo:

```bash
Devemos acessar as pastas chargeManager e chargeProxy em terminais diferentes
 - Primeiro Terminal: cd chargeManager
 - Segundo Terminal: cd chargeProxy
```

### Quarto passo:

```bash
Devemos instalar agora cada imagem referente aos charges da aplicação digitando em cada terminal específico 
 - Primeiro Terminal do ChargeManager: docker build --no-cache -t charge-manager-teste .
 - Segundo Terminal: cd chargeProxy: docker build --no-cache -t charge-proxy-teste .
```

### Quinto passo:

```bash
Por fim e ultimo passo, devemos estar no terminal do ChargeSystem e executar a aplicação através do docker swarm utilizando o seguinte comando
 - Terminal ChargeSystem:  docker stack deploy -c docker-stack.yml dac-project
OBS: Para parar a execução da aplicação use o comando docker stack rm dac-project no terminal
```

# Testando aplicação

Para testar a aplicação, devemos utilizar de alguma tecnologia para realizar requisições, recomendamos o uso do insomnia.

URL para requisição: http://localhost:8080/charges

### Método POST

BODY
```bash
{
	"amount":1.2,
	"customerName":"Gabriel"
}
```

Resposta
```bash
{
	"id": "11",
	"message": "Cobrança processada com sucesso",
	"status": "PENDING"
}
```
