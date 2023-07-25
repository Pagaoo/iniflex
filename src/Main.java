import model.Funcionario;
import model.Pessoa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();
        LocalDate dataAtual = LocalDate.now();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000,10,18), "Operador", new BigDecimal("2099.44")));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990,5,12), "Operador", new BigDecimal("2284.38")));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961,5,2), "Coordenador", new BigDecimal("9836.14")));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988,10,14), "Diretor", new BigDecimal("19119.88")));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), "Recepcionista", new BigDecimal("2234.68")));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999,11,19), "Operador", new BigDecimal("1582.72")));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993,3,31), "Contador", new BigDecimal("4071.84")));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994,7,8), "Gerente", new BigDecimal("3017.45")));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003,5,24), "Eletricista", new BigDecimal("1606.85")));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996,9,2), "Gerente", new BigDecimal("2799.93")));
        System.out.println("----------------Funcionarios cadastrados----------------");
        mostraFuncionarios(funcionarios);
        System.out.println("----------------Remover João----------------");
        removerFuncionario(funcionarios, "João");
        mostraFuncionarios(funcionarios);
        System.out.println("----------------Salarios atualizados----------------");
        calcularAumento(funcionarios);
        System.out.println("----------------funcionarios agrupados por cargo----------------");
        mostrarFuncionariosPorCargos(agruparPorFuncao(funcionarios));
        System.out.println("----------------funcionarios com aniversário nos mês 10 e 12----------------");
        List<Funcionario> aniversariantes = Aniversariantes(funcionarios, 10,12);
        mostraFuncionarios(aniversariantes);
        System.out.println("----------------funcionario com a maior idade----------------");
        Funcionario funcionarioMaiorIdade = funcionarioMaiorIdade(funcionarios);
        System.out.println("Nome: " + funcionarioMaiorIdade.getNome() + ", Idade: " + calcularIdade(funcionarioMaiorIdade.getDataNascimento()) + " anos");
        System.out.println("----------------funcionario cadastrados em ordem alfábetica----------------");
        List<Funcionario> funcionariosOrdenados = ordenarLista(funcionarios);
        mostraFuncionarios(funcionariosOrdenados);

    }

    private static void mostraFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario funcionario: funcionarios) {
            System.out.println(funcionario);
        }
    }

    private static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        Iterator<Funcionario> iterator = funcionarios.iterator();
        while (iterator.hasNext()) {
            Funcionario funcionario = iterator.next();
            if (funcionario.getNome().equals(nome)) {
                iterator.remove();
                System.out.println("Funcionario: " + nome + " removido com sucesso!");
                return;
            }
        }
        System.out.println("Funcionario: " + nome + " nao encontrado!");
    }

    private static void calcularAumento(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioAtual = funcionario.getSalario();
            BigDecimal salarioAtualizado = salarioAtual.multiply(BigDecimal.valueOf(1.1)).setScale(2, RoundingMode.UP);
            funcionario.setSalario(salarioAtualizado);
        }
        mostraFuncionarios(funcionarios);
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosAgrupados = new HashMap<>();

        for (Funcionario funcionario: funcionarios) {
            String funcao = funcionario.getFuncao();
            funcionariosAgrupados.computeIfAbsent(funcao,f -> new ArrayList<>()).add(funcionario);
        }
        return funcionariosAgrupados;
    }

    private static void mostrarFuncionariosPorCargos(Map<String, List<Funcionario>> funcionariosAgrupados) {
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosAgrupados.entrySet()) {
            String funcao = entry.getKey();
            List<Funcionario> funcionarios = entry.getValue();

            System.out.println("Função: " + funcao);
            for (Funcionario funcionario: funcionarios) {
                System.out.println("Nome: " + funcionario.getNome() + ", dataNascimento: " + funcionario.getDataNascimento() + ", Salario: " + funcionario.getSalario());
            }
        }
    }

    private static List<Funcionario> Aniversariantes(List<Funcionario> funcionarios, int... meses) {
        List<Funcionario> aniversariantes = new ArrayList<>();

        for (Funcionario funcionario: funcionarios) {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();

            for (int mes : meses) {
                if (mesAniversario == mes) {
                    aniversariantes.add(funcionario);
                }
            }
        }
        return aniversariantes;
    }

    private static Funcionario funcionarioMaiorIdade(List<Funcionario> funcionarios) {
        Funcionario funcionarioMaiorIdade = null;

        for (Funcionario funcionario: funcionarios) {
            LocalDate dataNascimento = funcionario.getDataNascimento();
            if (funcionarioMaiorIdade == null || dataNascimento.isBefore(funcionarioMaiorIdade.getDataNascimento())) {
                funcionarioMaiorIdade = funcionario;
            }
        }
        return funcionarioMaiorIdade;
    }

    private static int calcularIdade(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        return periodo.getYears();
    }

    private static List<Funcionario> ordenarLista(List<Funcionario> funcionarios) {
        funcionarios.sort(Comparator.comparing(Pessoa::getNome));
        return funcionarios;
    }
}
