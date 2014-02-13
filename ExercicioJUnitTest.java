package br.treinamento;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExercicioJUnitTest {

	private ExercicioJUnit negocio;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Executado, uma única vez, ANTES dos métodos de testes
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Inicio - ExercicioJUnitTest");
	}

	// Executado, uma única vez, DEPOIS dos métodos de testes
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Fim - ExercicioJUnitTest");
	}

	// Antes de todo teste
	@Before
	public void setUpBefore() throws Exception {
		negocio = new ExercicioJUnit();
	}

	// Depois de todo teste
	@After
	public void tearDownAfter() throws Exception {
		negocio = null;
	}

	@Test
	public void testValidarCamposObrigatoriosTodosPreenchidos() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));

		} catch (Exception e) {
			assertEquals("Campos com preenchimento obrigatório", e.getMessage());
		}
	}
	
	@Test
	public void testValidarCamposObrigatorios_Nome() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome(null);
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("Campo obrigatório 'Nome' não preenchido");

		} catch (Exception e) {
			assertEquals("O nome é obrigatório", e.getMessage());
		}
	}
	
	@Test
	public void testValidarCamposObrigatorios_NumeroDoc() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento(null);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("O número do documento é obrigatório");

		} catch (Exception e) {
			assertEquals("O número do documento é obrigatório", e.getMessage());
		}
	}
	
	@Test
	public void testValidarCamposObrigatorios_TipoDoc() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(null); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("O tipo do documento é obrigatório");

		} catch (Exception e) {
			assertEquals("O tipo do documento é obrigatório", e.getMessage());
		}
	}
	
	@Test
	public void testValidarCamposObrigatorios_DataInicial() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		testEntidade.setDataInicial(null);
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
		} catch (Exception e) {
			assertEquals("O período deve ser informado por completo", e.getMessage());
		}
	}
	
	@Test
	public void testValidarCamposObrigatorios_DataFinal() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		testEntidade.setDataFinal(null);
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("O período deve ser informado por completo");

		} catch (Exception e) {
			assertEquals("O período deve ser informado por completo", e.getMessage());
		}
	}
	

	
	// Validando as Regras
	@Test
	public void testValidarRegras_TodosOk() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));

		} catch (Exception e) {
			assertEquals("Campos com preenchimento inválido", e.getMessage());
		}
	}
	
	@Test
	public void testValidarRegras_NomeMenor() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("FALHA - O nome não pode ter menos que 5 caracteres");
		} catch (Exception e) {
			assertEquals("O nome não pode ter menos que 5 caracteres", e.getMessage());
		}
	}
	
	@Test
	public void testValidarRegras_NomeMaior() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa Magda Costa Magda Costa Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("FALHA - O nome não pode ter mais que 30 caracteres");
		} catch (Exception e) {
			assertEquals("O nome não pode ter mais que 30 caracteres", e.getMessage());
		}
	}
	
	@Test
	public void testValidarRegras_DtInicialMenorDtAtual() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2013, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("FALHA - A data inicial não pode ser menor que a data atual");
		} catch (Exception e) {
			assertEquals("A data inicial não pode ser menor que a data atual", e.getMessage());
		}
	}
	
	@Test
	public void testValidarRegras_DtFinalMenorDtInicial() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2013, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("FALHA - A data final não pode ser menor que a data inicial");
		} catch (Exception e) {
			assertEquals("A data final não pode ser menor que a data inicial", e.getMessage());
		}
	}
	
	@Test
	public void testValidarRegras_TipoDoc() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(3); // CPF
		testEntidade.setEmail("magda.costta@gmail.com");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("FALHA - Tipo de documento inválido");
		} catch (Exception e) {
			assertEquals("Tipo de documento inválido", e.getMessage());
		}
	}
	
	@Test
	public void testValidarRegras_Email() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magdacostta");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
			fail("FALHA - Endereço de email inválido");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
		
	}
	
	@Test
	public void testValidarRegras_Email2() throws Exception {
		
		Entidade testEntidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		testEntidade = new Entidade();
		testEntidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		testEntidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		testEntidade.setDataFinal(new Date(data.getTimeInMillis()));
		testEntidade.setNumeroDocumento((long) 999999);
		testEntidade.setTipoDocumento(1); // CPF
		testEntidade.setEmail("magda.costta");
		
		try {
			assertTrue(negocio.verificarEntidadeValida(testEntidade));
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
		
	}
	
	
	
	
	@Test
	public void testValidaCPF_Nulo() throws Exception {

		String cpf = "";
		assertFalse(negocio.validaCPF(cpf));

	}
	
	@Test
	public void testValidaCPF_Menor11Digitos() throws Exception {

		String cpf = "12345678";
		assertFalse(negocio.validaCPF(cpf));

	}
	
	@Test
	public void testValidaCPF_Maior11Digitos() throws Exception {

		String cpf = "12345678910111213";
		assertFalse(negocio.validaCPF(cpf));

	}
	
	@Test
	public void testValidaCPF_RetiraMascara() throws Exception {

		String cpf = "123.456.789-01";
		assertFalse(negocio.validaCPF(cpf));

	}
	
	@Test
	public void testValidaCPF_SoComZero() throws Exception {
		String cpf = "00000000000";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComHum() throws Exception {
		String cpf = "11111111111";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComDois() throws Exception {
		String cpf = "22222222222";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComTres() throws Exception {
		String cpf = "33333333333";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComQuatro() throws Exception {
		String cpf = "44444444444";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComCinco() throws Exception {
		String cpf = "55555555555";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComSeis() throws Exception {
		String cpf = "66666666666";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComSete() throws Exception {
		String cpf = "77777777777";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComOito() throws Exception {
		String cpf = "88888888888";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_SoComNove() throws Exception {
		String cpf = "99999999999";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_IncorretoPrimDigitoRestoHum() throws Exception {
		String cpf = "12345678900";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_IncorretoPrimDigitoRestoZero() throws Exception {
		String cpf = "22345678902";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_IncorretoPrimDigitoRestoDiferenteHumZero() throws Exception {
		String cpf = "01234567891";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_Incorreto4() throws Exception {
		String cpf = "21412525155";
		assertFalse(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_Valido() throws Exception {
		String cpf = "12345678909";
		assertTrue(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_Valido2() throws Exception {
		String cpf = "01234567890";
		assertTrue(negocio.validaCPF(cpf));
	}
	
	@Test
	public void testValidaCPF_Valido3() throws Exception {
		String cpf = "24650836190";
		assertTrue(negocio.validaCPF(cpf));
	}
}
