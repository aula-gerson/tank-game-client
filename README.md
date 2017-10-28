# Tank Game Client

Versão do jogo que roda do lado do cliente.

## Baixando e executando o projeto

### Dependências

* git
* conta no github
* eclipse IDE
* Java

### Baixando o projeto

* Com o terminal, acesse a sua pasta de *workspace* e execute o comando.

```{r, engine='bash', count_lines}
git clone https://github.com/aula-gerson/tank-game-client.git
```

### Importando projeto

No **eclipse**:

> File > import > General > Existing Projects into Workspace > **Browser...**

Selecione seu projeto dentro do seu *workspace*.


## Comandos  básicos do git

```{r, engine='bash', count_lines}
git add .
```
Prepara todos os arquivos alterados para serem **commitados**

```{r, engine='bash', count_lines}
git commit -m "message"
```

Cria um **commit** com as suas alterações, onde *message* é uma mensagem que você deve montar resumindo oque foi criado ou corrigido.

```{r, engine='bash', count_lines}
git push origin master
```

Envia todos os seus **commits** para o repositório remoto no *github*, para que as suas alterações fique disponiveis para todos da equipe.

```{r, engine='bash', count_lines}
git pull
```

Pega todos os **commits** do repositório remoto. Assim você consegue pegar todas as alterações feitas pelo resto da equipe.

### Importante

* O comando *git add .* deve ser utilizado sempre antes de um **commit**.

* O comando *git pull* deve ser utilizado sempre antes de criar um **commit** ou fazer um **push** para o repositório remoto.
