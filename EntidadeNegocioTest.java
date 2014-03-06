package br.treinamento;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EntidadeNegocioTest {

	private EntidadeNegocio entidadeNegocio;
	private EntidadeDAOInterface persistencia;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Executado, uma única vez, ANTES dos métodos de testes
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Inicio da classe de teste");
	}

	// Executado, uma única vez, DEPOIS dos métodos de testes
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Fim da classe de teste");
	}

	// Antes de todo teste
	@Before
	public void setUpBefore() throws Exception {
		entidadeNegocio = new EntidadeNegocio();
		// Criando o Mock
		persistencia = EasyMock.createMock(EntidadeDAOInterface.class);
		entidadeNegocio.setPersistencia(persistencia);
		
	}

	// Depois de todo teste
	@After
	public void tearDownAfter() throws Exception {
		entidadeNegocio = null;
		EasyMock.reset(persistencia);
	}
	
	
	// Entidade Valida, com todos os campos preenchidos corretamente
	private Entidade getEntidadeValida() {
		Entidade entidade = new Entidade();
		Calendar data = Calendar.getInstance();
		
		entidade.setId(1L);
		entidade.setNome("Magda Costa");
		data.set(2014, 3, 1);
		entidade.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2014, 3, 31);
		entidade.setDataFinal(new Date(data.getTimeInMillis()));
		entidade.setNumeroDocumento((long) 999999);
		entidade.setTipoDocumento(1); // CPF
		
		data = Calendar.getInstance();
		entidade.setDataGravacao(new Date(data.getTimeInMillis()));
		entidade.setEmail("magda.costta@gmail.com");
		
		return entidade;
	}
	
	
	@Test
	public void testFalhaValidarCamposObrigatorios_NomeNaoPreenchido() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("O nome é obrigatório");
	    
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNome(null);
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarCamposObrigatorios_NumDocNaoPreenchido() throws Exception {
		
		thrown.expect(Exception.class);
	    thrown.expectMessage("O número do documento é obrigatório");
	    
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNumeroDocumento(null);
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarCamposObrigatorios_TipoDocNaoPreenchido() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("O tipo do documento é obrigatório");
	    
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setTipoDocumento(null);
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarCamposObrigatorios_DtFinalNaoPreenchido() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("O período deve ser informado por completo");
	    
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setDataFinal(null);
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarRegras_NomeMenor() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("O nome não pode ter menos que 5 caracteres");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNome("Ana");
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarRegras_NomeMaior() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("O nome não pode ter mais que 30 caracteres");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNome("Teste Nome Maior Que 30 Caracteres Nome Nome");
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarRegras_NumeroDoc() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("O número do documento deve ser maior que zero");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNumeroDocumento((long) 0);
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarRegras_DtInicialMenorDtAtual() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("A data inicial não pode ser menor que a data atual");
		
		Entidade entidadeRetornada = getEntidadeValida();
		Calendar data = Calendar.getInstance();
		
		data.set(2013, 3, 1);
		entidadeRetornada.setDataInicial(data.getTime());
		
		data.set(2014, 3, 31);
		entidadeRetornada.setDataFinal(data.getTime());
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarRegras_DtFinalMenorDtInicial() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("A data final não pode ser menor que a data inicial");
		
		Entidade entidadeRetornada = getEntidadeValida();
		Calendar data = Calendar.getInstance();
		
		data.set(2014, 3, 1);
		entidadeRetornada.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2013, 3, 31);
		entidadeRetornada.setDataFinal(new Date(data.getTimeInMillis()));
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarRegras_TipoDoc() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("Tipo de documento inválido");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setTipoDocumento(3);
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testFalhaValidarRegras_Email() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("Endereço de email inválido");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setEmail("magdacostta");
		
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidadeRetornada);

		EasyMock.verify(persistencia);
		
	}
	
	@Test
	public void testOkSalvar() throws Exception{
		Entidade entidade = getEntidadeValida();
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setId(1L);
		
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(true);
		EasyMock.expect(persistencia.salvar(entidade)).andReturn(entidadeRetornada);
		EasyMock.replay(persistencia);
		
		Entidade entidadePersistida = entidadeNegocio.salvar(entidade);
		assertNotNull(entidadePersistida.getId());
		
		EasyMock.verify(persistencia); 
	}
	
	@Test
	public void testOkSalvar2() throws Exception{
		Entidade entidade = getEntidadeValida();
		entidade.setId(2L);
		entidade.setDataInicial(null);
		entidade.setDataFinal(null);
		entidade.setTipoDocumento(2);
		entidade.setEmail(null);
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setId(2L);
		entidadeRetornada.setDataInicial(null);
		entidadeRetornada.setDataFinal(null);
		entidadeRetornada.setTipoDocumento(2);
		entidadeRetornada.setEmail(null);
		
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(true);
		EasyMock.expect(persistencia.salvar(entidade)).andReturn(entidadeRetornada);
		EasyMock.replay(persistencia);
		
		Entidade entidadePersistida = entidadeNegocio.salvar(entidade);
		assertNotNull(entidadePersistida.getId());
		
		EasyMock.verify(persistencia); 
	}
	
	
	@Test
	public void testFalhaSalvarEntidadeComMesmoNome() throws Exception{
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("Já existe entidade cadastrada com este nome.");
	    
		Entidade entidade = getEntidadeValida();

		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(false);
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidade);
	
		EasyMock.verify(persistencia); 
	}
	
	@Test
	public void testOkAlterar() throws Exception {
		
		Entidade entidade = getEntidadeValida();
		entidade.setId(1L);

		Entidade entidadeRetornadaPeloId = getEntidadeValida();
		entidadeRetornadaPeloId.setId(1L);

		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setId(2L);

		EasyMock.expect(persistencia.getById(1L)).andReturn(entidadeRetornadaPeloId);
		EasyMock.expect(persistencia.alterar(entidade)).andReturn(entidadeRetornada);
		EasyMock.replay(persistencia);

		entidadeNegocio.alterar(entidade);

		EasyMock.verify(persistencia);
	}	
	
	@Test
	public void testFalhaAlterar() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("Não é possível alterar o nome da entidade");
	    
		Entidade entidade = getEntidadeValida();
		entidade.setNome("Magda Silveira");
		entidade.setId(1L);

		Entidade entidadeRetornadaGetById = getEntidadeValida();
		entidadeRetornadaGetById.setId(1L);

		EasyMock.expect(persistencia.getById(1L)).andReturn(entidadeRetornadaGetById);
		EasyMock.replay(persistencia);

		entidade = entidadeNegocio.alterar(entidade);

		EasyMock.verify(persistencia);
	}

	@Test
	public void testOkExcluirTipoDoc2() throws Exception {
		
		Entidade entidade = getEntidadeValida();
		entidade.setTipoDocumento(2);

        persistencia.excluir(EasyMock.eq(entidade));
        EasyMock.replay(persistencia);

        entidadeNegocio.excluir(entidade);

        EasyMock.verify(persistencia);
        
	}

	@Test
	public void testFalhaExcluirCPF() throws Exception {
		
		thrown.expect(Exception.class);
	    thrown.expectMessage("Não é possível excluir entidades com cpf");
	    
		Entidade entidade = getEntidadeValida();

        persistencia.excluir(EasyMock.eq(entidade));
        EasyMock.replay(persistencia);

        entidadeNegocio.excluir(entidade);

		EasyMock.verify(persistencia);
	}

	//
	@Test
	public void testGetQtdeRegistros() throws Exception {
		int qtdeRegistros;
		int qtdeRegistrosRetornados = 10;

		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(qtdeRegistrosRetornados);
		EasyMock.replay(persistencia);

		qtdeRegistros = entidadeNegocio.getQuantidadeRegistros();

		assertEquals(qtdeRegistrosRetornados, qtdeRegistros);

		EasyMock.verify(persistencia);
	}	
	
	@Test
	public void testVerificarUnicidadeNome() throws Exception {
		Entidade entidade = getEntidadeValida();

		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(true);
		EasyMock.replay(persistencia);

		entidadeNegocio.verificarUnicidadeNome(entidade);
	}	

	
	/*Criar um ÚNICO método que:
		 Verifique a quantidade de registros;
		 Insere um registro;
		 Verifica que a quantidade foi incrementada
		 Tenta inserir o mesmo registro mas recebe uma exceção
		 Excluir o registro
		 Verifica que a quantidade foi decrementada*/
	
	@Test
	public void testOkUnicoMetodo() throws Exception {
		
		// Verifique a quantidade de registros
		int qtdeRegistros;
		int qtdeRegistrosRetornados = 10;

		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(qtdeRegistrosRetornados);
		EasyMock.replay(persistencia);

		qtdeRegistros = entidadeNegocio.getQuantidadeRegistros();

		assertEquals(qtdeRegistrosRetornados, qtdeRegistros);

		EasyMock.verify(persistencia);
		
		
		// Insere um registro
		Entidade entidade = getEntidadeValida();
		entidade.setNome("Carlos Eduardo");
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNome("Carlos Eduardo");
		
		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(true);
		EasyMock.expect(persistencia.salvar(entidade)).andReturn(entidadeRetornada);
		EasyMock.replay(persistencia);
		
		Entidade entidadePersistida = entidadeNegocio.salvar(entidade);
		assertNotNull(entidadePersistida.getId());
		
		EasyMock.verify(persistencia); 
		
		
		// Verifica que a quantidade foi incrementada 
		qtdeRegistros = 0;
		qtdeRegistrosRetornados = 11;

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(qtdeRegistrosRetornados);
		EasyMock.replay(persistencia);

		qtdeRegistros = entidadeNegocio.getQuantidadeRegistros();

		assertEquals(qtdeRegistrosRetornados, qtdeRegistros);
		
		// Tenta inserir o mesmo registro mas recebe uma exceção
		thrown.expect(Exception.class );
	    thrown.expectMessage("Já existe entidade cadastrada com este nome.");
	    
		Entidade entidade1 = getEntidadeValida();

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade1)).andReturn(false);
		EasyMock.replay(persistencia);
		
		entidadeNegocio.salvar(entidade1);
	
		EasyMock.verify(persistencia); 
		
		// Excluir o registro
		Entidade entidade3 = getEntidadeValida();
		entidade3.setTipoDocumento(2);

        persistencia.excluir(EasyMock.eq(entidade3));
        EasyMock.replay(persistencia);

        entidadeNegocio.excluir(entidade3);

        EasyMock.verify(persistencia);
        
		
		// Verifica que a quantidade foi incrementada 
		qtdeRegistros = 0;
		qtdeRegistrosRetornados = 10;

		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(qtdeRegistrosRetornados);
		EasyMock.replay(persistencia);

		qtdeRegistros = entidadeNegocio.getQuantidadeRegistros();

		assertEquals(qtdeRegistrosRetornados, qtdeRegistros);
		
		EasyMock.verify(persistencia);
	}	


	
}
