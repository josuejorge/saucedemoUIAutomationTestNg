# SauceDemo UI Automation — TestNG

Automação de testes E2E com **Java + Maven + TestNG + Selenium 4** para o site [https://www.saucedemo.com](https://www.saucedemo.com).

<img width="894" height="379" alt="image" src="https://github.com/user-attachments/assets/e23f25f5-4d8e-4e84-9cf8-62e4b52957b7" />

<img width="1887" height="891" alt="image" src="https://github.com/user-attachments/assets/9dc15351-40a5-4f3b-ac14-34dbcf1ea022" />

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Maven | 3.9+ |
| Selenium | 4.27.0 |
| TestNG | 7.10.2 |
| Google Chrome | qualquer (Selenium Manager baixa o driver automaticamente) |

---

## Pré-requisitos

- **Java 21** instalado e configurado no `JAVA_HOME`
- **Maven 3.9+** disponível no PATH
- **Google Chrome** instalado

Verificar instalações:
```bash
java -version
mvn -version
```

---

## Estrutura do Projeto

```
src/
├── main/java/
│   ├── config/
│   │   └── ConfigReader.java       # Lê o config.properties
│   ├── driver/
│   │   └── DriverFactory.java      # Cria e gerencia o WebDriver com ThreadLocal
│   └── pages/
│       ├── LoginPage.java
│       ├── HomePage.java
│       ├── ProductPage.java
│       ├── CartPage.java
│       └── CheckoutPage.java
├── main/resources/
│   └── config.properties           # URL base e credenciais
└── test/java/
    ├── base/
    │   └── BaseTest.java           # Ciclo de vida do WebDriver por classe
    └── tests/
        ├── LoginTest.java
        ├── HomeTest.java
        ├── CardsTest.java
        ├── CartTest.java
        └── CheckoutTest.java
```

---

## Cenários de Teste

### Login — 6 cenários
- Validar login com sucesso
- Validar login com credenciais inválidas
- Validar login com campos vazios
- Validar login com usuário vazio
- Validar login com senha vazia
- Validar login com usuário bloqueado

### Home — 8 cenários
- Validar que a home carregou com sucesso
- Validar quantidade de produtos exibidos (6)
- Validar badge ao adicionar item ao carrinho
- Validar remover item da home
- Validar ordenação por nome A → Z
- Validar ordenação por nome Z → A
- Validar ordenação por preço menor → maior
- Validar ordenação por preço maior → menor

### Cards / Produto — 6 cenários
- Validar que página de detalhe do produto abriu
- Validar nome do produto na página de detalhe
- Validar preço do produto na página de detalhe
- Validar imagem do produto na página de detalhe
- Validar adicionar ao carrinho pela página de detalhe
- Validar botão voltar para a home

### Cart — 4 cenários
- Validar que item adicionado aparece no carrinho
- Validar badge do carrinho ao adicionar item
- Validar remover item do carrinho
- Validar continuar comprando a partir do carrinho

### Checkout — 5 cenários
- Validar checkout sem preencher informações
- Validar checkout sem sobrenome
- Validar checkout sem CEP
- Validar compra completa com sucesso
- Validar cancelar compra e voltar ao carrinho

**Total: 29 cenários automatizados**

---

## Executando os Testes

### Instalar dependências
```bash
mvn install -DskipTests
```

### Rodar todos os testes
```bash
mvn test
```

Os testes rodam em **paralelo** — cada suite em uma thread separada com seu próprio browser, executando as 5 suites simultaneamente.

### Rodar uma suite específica
```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=HomeTest
mvn test -Dtest=CartTest
mvn test -Dtest=CardsTest
mvn test -Dtest=CheckoutTest
```

---

## Paralelismo

Configurado no `testng.xml`:

```xml
<suite parallel="tests" thread-count="5">
```

Cada `<test>` (suite) roda em uma thread com seu próprio WebDriver isolado via `ThreadLocal`. O browser abre **uma vez por suite** (`@BeforeClass`) e fica aberto enquanto todos os métodos daquela suite executam, eliminando o overhead de abrir e fechar o Chrome a cada teste.

| Estratégia | Browsers abertos | Tempo estimado |
|---|---|---|
| Sequencial sem `@BeforeClass` | 29 (1 por método) | ~3:55 min |
| Paralelo com `@BeforeClass` | 5 (1 por suite) | ~1:00 min |

---

## Configuração

As credenciais e a URL base ficam em [`src/main/resources/config.properties`](src/main/resources/config.properties):

```properties
base.url=https://www.saucedemo.com
username=standard_user
password=secret_sauce
locked.username=locked_out_user
invalid.username=invalid_user
invalid.password=wrong_password
```

---

## .gitignore

Os seguintes artefatos são ignorados pelo git:

```
target/          # build Maven
*.jar / *.class  # binários compilados
test-output/     # relatório HTML padrão do TestNG
screenshots/     # capturas de tela dos testes
evidence/        # PDFs de evidência
.idea/ / *.iml   # configurações do IntelliJ
```
