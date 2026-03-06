# WildFly AI Demo

Conversational AI REST API built with **WildFly 39**, **LangChain4j**, and **Ollama** (gemma:2b). Demonstrates enterprise Java integration with local LLM for text analysis and conversational responses.

## Architecture

```
src/main/java/com/demo/ai/
├── Analyst.java         # AI service interface with conversational prompt
├── AnalystService.java  # Ollama chat model integration (gemma:2b)
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

### 3. Configure Ollama
```bash
# Set Ollama model (defaults to gemma:2b if not set)
ssh cloud-user@<server> "sudo sed -i 's/OLLAMA_MODEL=.*/OLLAMA_MODEL=gemma:2b/' /etc/sysconfig/wildfly.conf && sudo systemctl restart wildfly"

# Optional: Set Ollama base URL (defaults to http://localhost:11434)
ssh cloud-user@<server> "sudo sed -i 's/OLLAMA_BASE_URL=.*/OLLAMA_BASE_URL=http://localhost:11434/' /etc/sysconfig/wildfly.conf && sudo systemctl restart wildfly"
```

### 4. Test
```bash
curl -X POST http://<server>:8380/ai-demo/api/demo/check \
  -H "Content-Type: text/plain" \
  -d "This product is amazing!"

# Sample response:
# Thank you for the positive feedback! As a helpful AI assistant,
# I am always delighted to hear that my work is making a difference.
# I am happy to provide any additional insights or answer any
# questions you may have about the product.
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
- **Ollama** gemma:2b (configurable)

## API

**POST** `/ai-demo/api/demo/check`
- **Content-Type:** `text/plain`
- **Body:** Any text input
- **Returns:** Conversational AI response using Ollama's gemma:2b model
