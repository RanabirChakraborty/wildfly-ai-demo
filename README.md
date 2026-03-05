# WildFly AI Demo

AI-powered sentiment analysis REST API built with **WildFly 39** and **LangChain4j** using **OpenAI**.

## Architecture

```
src/main/java/com/demo/ai/
├── Analyst.java         # AI service interface (LangChain4j)
├── AnalystService.java  # OpenAI chat model integration
├── DemoResource.java    # REST endpoint (JAX-RS)
└── RestConfig.java      # REST configuration

ansible/
├── site.yml             # Main playbook
├── inventory/           # Server definitions
└── group_vars/all.yml   # Configuration variables
```

## Quick Start

### 1. Build
```bash
mvn clean package
```

### 2. Deploy with Ansible
```bash
ansible-galaxy collection install middleware_automation.wildfly
cd ansible
ansible-playbook -i inventory site.yml --tags deploy
```

### 3. Configure OpenAI
```bash
ssh cloud-user@<server> "sudo sed -i 's/OPENAI_API_KEY=.*/OPENAI_API_KEY=your-key/' /etc/sysconfig/wildfly.conf && sudo systemctl restart wildfly"
```

### 4. Test
```bash
curl -X POST http://<server>:8380/ai-demo/api/demo/check \
  -H "Content-Type: text/plain" \
  -d "This product is amazing!"
```

## Configuration

Edit `ansible/group_vars/all.yml`:
```yaml
wildfly_version: "39.0.0.Final"
wildfly_port_http: 8380
java_home: "/usr/lib/jvm/java-17-openjdk"
```

## Stack

- **WildFly** 39.0.0.Final (Java 17)
- **LangChain4j** 0.35.0
- **Jakarta EE 10** (CDI, JAX-RS)
- **OpenAI** gpt-3.5-turbo

## API

**POST** `/ai-demo/api/demo/check`
- **Content-Type:** `text/plain`
- **Returns:** AI-generated sentiment analysis
