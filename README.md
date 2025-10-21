# Sistema de Reserva de Voos
Sistema de gestão de reservas de voos com interface gráfica desenvolvido em Java Swing.

## Arquitetura
- **JanelaPrincipal**: Interface gráfica principal
- **GestorDeVoos**: Gestão centralizada de voos e reservas
- **Voo**: Gestão individual de cada voo
- **Passageiro**: Dados dos passageiros
- **Assento**: Assentos (Executivo/Econômico)
- **Recibo**: Registro de pagamentos e extras

## Funcionalidades
- Reserva de assentos
- Gestão de assentos ocupados
- Gestão de reservas (alteração/cancelamento)
- Extras: Seguro, Bagagem Extra, Check-in Automático
- Múltiplos métodos de pagamento
- Persistência de dados entre sessões

## Estrutura
- 30 assentos por voo (6 executivos + 24 econômicos)
- Validação de NIF português
- Sistema de preços dinâmico


## Execução
```bash
Compilação: javac -d . src/*.java
Run: java Main
```


## Licença
GNU v3.0

