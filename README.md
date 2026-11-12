# Sugestão de Amizades por Amigos em Comum

Trabalho de Estrutura de Dados e Algoritmos: um grafo não direcionado e não
ponderado representa uma rede social simples. O programa sugere novas
amizades para uma pessoa com base em quantos amigos em comum ela tem com
quem ainda não é amiga.

## Como funciona

- O grafo é guardado como lista de adjacência (`Map<String, List<String>>`).
- `sugerir(x)` percorre os amigos de `x` e, para cada amigo, percorre os
  amigos dele, contando quantas vezes cada pessoa (que não é `x` e ainda não
  é amiga de `x`) aparece.
- `ordenar(...)` ordena o resultado do maior número de amigos em comum para
  o menor, desempatando em ordem alfabética.
- `testes()` verifica os casos principais e imprime `OK`/`FALHOU` para cada um.

## Como compilar e executar

```
javac -encoding UTF-8 Sugestoes.java
java Sugestoes
```

Se os acentos aparecerem trocados no terminal do Windows, troque a página
de código para UTF-8 antes de rodar:

```
chcp 65001
java Sugestoes
```

## Saída esperada

```
Ana -> [Diego=2, Elisa=1]
Felipe -> [Carla=2, Bruno=1]
Gabi -> nenhuma sugestão
OK - Ana -> Diego(2), Elisa(1)
OK - Felipe -> Carla(2), Bruno(1)
OK - Gabi (isolada) -> nenhuma sugestão
OK - Felipe e Ana não se sugerem (distância 3)
```
