import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Sugestão de amizades usando grafo de amigos em comum.
public class Sugestoes {

    // Grafo não direcionado: cada pessoa aponta para a lista de amigos dela.
    static Map<String, List<String>> g = new HashMap<>();

    // Cria o vértice da pessoa, se ele ainda não existir.
    static void adicionarPessoa(String nome) {
        g.putIfAbsent(nome, new ArrayList<>());
    }

    // Cria uma amizade (aresta) nos dois sentidos.
    static void amizade(String a, String b) {
        g.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
        g.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
    }

    // Conta quantos amigos em comum X tem com cada pessoa que ainda não é amiga dele.
    static Map<String, Integer> sugerir(String x) {
        Map<String, Integer> cont = new HashMap<>();
        List<String> amigos = g.getOrDefault(x, List.of());

        for (String amigo : amigos)
            for (String c : g.get(amigo))
                if (!c.equals(x) && !amigos.contains(c))
                    cont.merge(c, 1, Integer::sum);

        return cont;
    }

    // Ordena as sugestões: mais amigos em comum primeiro; empate, ordem alfabética.
    static List<Map.Entry<String, Integer>> ordenar(Map<String, Integer> cont) {
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(cont.entrySet());
        lista.sort((e1, e2) -> {
            int cmp = e2.getValue() - e1.getValue();
            if (cmp != 0) return cmp;
            return e1.getKey().compareTo(e2.getKey());
        });
        return lista;
    }

    // Imprime a sugestão de uma pessoa no formato pedido.
    static void imprimirSugestao(String nome) {
        List<Map.Entry<String, Integer>> lista = ordenar(sugerir(nome));
        if (lista.isEmpty())
            System.out.println(nome + " -> nenhuma sugestão");
        else
            System.out.println(nome + " -> " + lista);
    }

    // Verifica se a lista de sugestões contém uma determinada pessoa.
    static boolean temPessoa(List<Map.Entry<String, Integer>> lista, String nome) {
        for (Map.Entry<String, Integer> e : lista)
            if (e.getKey().equals(nome)) return true;
        return false;
    }

    // Compara o resultado obtido com o esperado e imprime OK ou FALHOU.
    static void teste(String descricao, boolean passou) {
        System.out.println((passou ? "OK" : "FALHOU") + " - " + descricao);
    }

    static void testes() {
        teste("Ana -> Diego(2), Elisa(1)",
                ordenar(sugerir("Ana")).equals(List.of(Map.entry("Diego", 2), Map.entry("Elisa", 1))));

        teste("Felipe -> Carla(2), Bruno(1)",
                ordenar(sugerir("Felipe")).equals(List.of(Map.entry("Carla", 2), Map.entry("Bruno", 1))));

        teste("Gabi (isolada) -> nenhuma sugestão",
                ordenar(sugerir("Gabi")).isEmpty());

        // Caso não óbvio: Felipe e Ana estão a distância 3, não devem se sugerir.
        boolean semFelipeNaAna = !temPessoa(ordenar(sugerir("Ana")), "Felipe");
        boolean semAnaNoFelipe = !temPessoa(ordenar(sugerir("Felipe")), "Ana");
        teste("Felipe e Ana não se sugerem (distância 3)", semFelipeNaAna && semAnaNoFelipe);
    }

    public static void main(String[] args) {
        adicionarPessoa("Ana");
        adicionarPessoa("Bruno");
        adicionarPessoa("Carla");
        adicionarPessoa("Diego");
        adicionarPessoa("Elisa");
        adicionarPessoa("Felipe");
        adicionarPessoa("Gabi");

        amizade("Ana", "Bruno");
        amizade("Ana", "Carla");
        amizade("Bruno", "Diego");
        amizade("Carla", "Diego");
        amizade("Carla", "Elisa");
        amizade("Diego", "Felipe");
        amizade("Elisa", "Felipe");

        imprimirSugestao("Ana");
        imprimirSugestao("Felipe");
        imprimirSugestao("Gabi");

        testes();
    }
}
