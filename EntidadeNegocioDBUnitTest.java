package br.treinamento;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.Calendar;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EntidadeNegocioDBUnitTest extends DatabaseTestCase {

	private EntidadeNegocio entidadeNegocio;
	private EntidadeDAOInterface persistencia;

	private final static String TABELA_ENTIDADE = "entidade";

	private IDataSet loadedDataSet;

	protected IDatabaseConnection getConnection() throws Exception {
		DatabaseConnection con = new DatabaseConnection(ConnectionFactory.getConnection());
		DatabaseConfig config = con.getConfig();

		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

		return con;
	}

	protected IDataSet getDataSet() throws Exception {
		loadedDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("entidade.xml"));
		return loadedDataSet;
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		entidadeNegocio = new EntidadeNegocio();
		persistencia = new EntidadeDAO(); //EasyMock.createMock(EntidadeDAOInterface.class);
		entidadeNegocio.setPersistencia(persistencia);

		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	}

	@After
	public void after() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
	}

	@Test
	public void testFalhaValidarCamposObrigatorios_NomeNaoPreenchido() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("O nome é obrigatório");
	    
		Entidade entidade = getEntidadeValida();
		entidade.setNome(null);
		
		entidadeNegocio.salvar(entidade);

	}
	
	@Test
	public void testFalhaValidarCamposObrigatorios_NumDocNaoPreenchido() throws Exception {
		
		thrown.expect(Exception.class);
	    thrown.expectMessage("O número do documento é obrigatório");
	    
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNumeroDocumento(null);
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarCamposObrigatorios_TipoDocNaoPreenchido() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("O tipo do documento é obrigatório");
	    
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setTipoDocumento(null);
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarCamposObrigatorios_DtFinalNaoPreenchido() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("O período deve ser informado por completo");
	    
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setDataFinal(null);
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarRegras_NomeMenor() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("O nome não pode ter menos que 5 caracteres");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNome("Ana");
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarRegras_NomeMaior() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("O nome não pode ter mais que 30 caracteres");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNome("Teste Nome Maior Que 30 Caracteres Nome Nome");
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarRegras_NumeroDoc() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("O número do documento deve ser maior que zero");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setNumeroDocumento((long) 0);
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarRegras_DtInicialMenorDtAtual() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("A data inicial não pode ser menor que a data atual");
		
		Entidade entidadeRetornada = getEntidadeValida();
		Calendar data = Calendar.getInstance();
		
		data.set(2013, 3, 21);
		entidadeRetornada.setDataInicial(data.getTime());
		
		data.set(2014, 3, 31);
		entidadeRetornada.setDataFinal(data.getTime());
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarRegras_DtFinalMenorDtInicial() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("A data final não pode ser menor que a data inicial");
		
		Entidade entidadeRetornada = getEntidadeValida();
		Calendar data = Calendar.getInstance();
		
		data.set(2014, 3, 21);
		entidadeRetornada.setDataInicial(new Date(data.getTimeInMillis()));
		
		data.set(2013, 3, 31);
		entidadeRetornada.setDataFinal(new Date(data.getTimeInMillis()));
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarRegras_TipoDoc() throws Exception {

		thrown.expect(Exception.class );
	    thrown.expectMessage("Tipo de documento inválido");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setTipoDocumento(3);
		
		entidadeNegocio.salvar(entidadeRetornada);

	}
	
	@Test
	public void testFalhaValidarRegras_Email() throws Exception {
		
		thrown.expect(Exception.class );
	    thrown.expectMessage("Endereço de email inválido");
		
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setEmail("magdacostta");
		
		entidadeNegocio.salvar(entidadeRetornada);

	}

	
	public Entidade getEntidadeValida() throws Exception {

		Entidade entidade = new Entidade();
		Calendar data = Calendar.getInstance();

		entidade.setId(1L);
		entidade.setNome("Magda");
		entidade.setEmail("magda@gmail.com");

		data.set(2014, 3, 21);
		entidade.setDataInicial(new Date(data.getTimeInMillis()));

		data.set(2014, 3, 31);
		entidade.setDataFinal(new Date(data.getTimeInMillis()));

		entidade.setNumeroDocumento(999999L);
		entidade.setTipoDocumento(1); // 1-CPF 2-

		data = Calendar.getInstance();
		entidade.setDataGravacao(new Date(data.getTimeInMillis()));

		return entidade;
	}

	@Test
	public void testOkSalvar() throws Exception{
		
		Entidade entidade = getEntidadeValida();
		
		entidade.setId(3L);
		entidade.setNome("Joao Antonio");
		entidade.setEmail("joao@gmail.com");
		entidade.setNumeroDocumento(909090L);
		entidade.setTipoDocumento(1); // CPF
		
		IDataSet baseEsperada = new FlatXmlDataSetBuilder().build(new FileInputStream("baseEsperada_insert.xml"));
		ITable dadosEsperado = baseEsperada.getTable(TABELA_ENTIDADE);

		entidadeNegocio.salvar(entidade);
		
        assertEquals(baseEsperada.getTable(TABELA_ENTIDADE).getRowCount(), entidadeNegocio.getQuantidadeRegistros());

	}
	
	@Test
	public void testFalhaSalvar() throws Exception{
		
		thrown.expect(Exception.class);
		thrown.expectMessage("Já existe entidade cadastrada com este nome.");
				
		Entidade entidade = getEntidadeValida();
		
		entidade.setId(4L);
		entidade.setNome("Magda");
		entidade.setEmail("joao@gmail.com");
		entidade.setNumeroDocumento(909090L);
		entidade.setTipoDocumento(1); // CPF
		
		IDataSet baseEsperada = new FlatXmlDataSetBuilder().build(new FileInputStream("baseEsperada_insert.xml"));
		ITable dadosEsperado = baseEsperada.getTable(TABELA_ENTIDADE);
		
        assertTrue(entidadeNegocio.verificarUnicidadeNome(entidade));
        
        entidadeNegocio.salvar(entidade);

	}
	
	@Test
	public void testOkAlterar() throws Exception { 
		Entidade entidade = entidadeNegocio.getById(2L);
		entidade.setEmail("jandrade@gmail.com");

		Entidade entidadeRetornadaById = entidadeNegocio.alterar(entidade);

		Entidade entidadeRetornada = entidadeNegocio.getById(2L);

		assertTrue(entidadeRetornada.equals(entidadeRetornadaById));
	}	
	
	@Test
	public void testFalhaAlterar() throws Exception {
		
		thrown.expect(Exception.class);
		thrown.expectMessage("Já existe entidade cadastrada com este nome.");
		
		Entidade entidade = entidadeNegocio.getById(3L);
		entidade.setNome("Magda");
		
		entidadeNegocio.alterar(entidade);
	}
	
	@Test
	public void testFalhaAlterarMesmoNome() throws Exception {
		
		thrown.expect(Exception.class);
		thrown.expectMessage("Não é possível alterar o nome da entidade");
		
		Entidade entidade = entidadeNegocio.getById(1L);
		entidade.setNome("Magda Costa");
		
		entidadeNegocio.alterar(entidade);
	}
	
	@Test
	public void testOkExcluir() throws Exception {
		int qtdeRegistros;

		Entidade entidade = getEntidadeValida();
		entidade.setTipoDocumento(2);
		entidade.setId(3L);
		
		qtdeRegistros = entidadeNegocio.getQuantidadeRegistros()-1;
		entidadeNegocio.excluir(entidade);

		assertEquals(entidadeNegocio.getQuantidadeRegistros(), entidadeNegocio.getQuantidadeRegistros());
	}
    
	@Test
	public void testFalhaExcluir() throws Exception {
		
		thrown.expect(Exception.class);
		thrown.expectMessage("Não é possível excluir entidades com cpf");

		Entidade entidade = getEntidadeValida();
		entidade.setId(1L);
		
		entidadeNegocio.excluir(entidade);
	}
	

	@Test
	public void testCheckDataLoaded() throws Exception {
		assertNotNull(loadedDataSet);
		int rowCount = loadedDataSet.getTable(TABELA_ENTIDADE).getRowCount();
		assertEquals(2, rowCount);
	}

}
