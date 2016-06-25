import org.codehaus.groovy.scriptom.*
import groovy.sql.Sql;
import org.codehaus.groovy.reflection.ReflectionUtils

sql = Sql.newInstance("jdbc:postgresql://localhost:" +
	   "5432/pyme",'ERPUser', 'ERPUser',
	   "org.postgresql.Driver")

// Colocar la serie de la planta correspondiente
// 323 NARDO, 440 PRADERA
PLANTA = 'pradera'
idSerie = PLANTA == 'pradera'? 440:323
NULL = 'NULL'
salida = []
salida << "BEGIN;"

def archivoCarga = new FileInputStream(new File("/home/pi/Dropbox/GM3Work/reycosa/migracion/${PLANTA}.csv"))
	   
idsCliente = [:]
def getIdCliente(cod){
	if(idsCliente[cod] == null){
		def id = sql.firstRow("select si_id_m_cliente from catalogos.m_cliente where vi_codigousuario = ? and si_id_m_empresa = 55;", cod as Integer)[0]
		idsCliente[cod] = id
	}
	idsCliente[cod]
}

idsArticulo = [:]
def getIdArticulo(nomcorto){
	//println nomcorto
	if(idsArticulo[nomcorto] == null){
		def id = sql.firstRow("select si_id_m_articulo from m_articulo where vc_nombrecorto = ? and si_id_m_empresa = 55;", nomcorto)[0]
		idsArticulo[nomcorto] = id
	}
	idsArticulo[nomcorto]
}

idsBodega = [:]
def getIdBodega(cod){
	if(idsBodega[cod] == null){
		def id = sql.firstRow("select mb.si_id_m_bodega from m_almacen ma left join m_bodega mb on (ma.si_id_m_almacen = mb.si_id_m_almacen) where mb.vi_codigousuario = ? and ma.si_id_m_empresa = 55;", cod as Integer)[0]
		idsBodega[cod] = id
	}
	idsBodega[cod]
}

idsEmpaques = [:]
def getIdsEmpaques(cods){
	//println cods
	def ids = []
	cods.each { cod ->
		if(idsEmpaques[cod] == null){
			def id = sql.firstRow("""select mc.si_id_m_catalogo from catalogos.t_catalogo tc left join catalogos.m_catalogo mc on (tc.si_id_t_catalogo = mc.si_id_t_catalogo)
                                        where mc.vi_codigousuario = ? and tc.si_id_m_empresa = 55 and tc.sc_clavecatalogo = '1209';""", cod as Integer)[0]
			idsEmpaques[cod] = id
		}
		ids.add idsEmpaques[cod]
	}
	
	return ids;
}

idsUnidad = [:]
def getIdUnidad(cod){
	if(idsUnidad[cod] == null){
		def id = sql.firstRow("""select mc.si_id_m_catalogo from catalogos.t_catalogo tc left join catalogos.m_catalogo mc on (tc.si_id_t_catalogo = mc.si_id_t_catalogo)
                                        where mc.vi_codigousuario = ? and tc.si_id_m_empresa = 55 and tc.sc_clavecatalogo = '1208';""", cod as Integer)[0]
		idsUnidad[cod] = id
	}
	idsUnidad[cod]
}

def escribirArchivo(def rutaArchivo, def data) {
	new File("${rutaArchivo}").withWriter { out ->
	  data.each {
		out.println it
	  }
	}
}

def rowsArchivo = archivoCarga.text.split '\n'
//println rowsArchivo[0]
def tokensArchivo = rowsArchivo.collect {
	it.split(',').collect {
		it.trim() == '' ? NULL:it
	}
}
//println tokensArchivo[2][8]

def idEntrada = 0
def idCargo = 0
def datos = tokensArchivo.collect { row ->
		
	row[13] = row[13]?.split('%') as List
	row[13] = row[13].grep {it != '0'}
	row[12] = row[12]?.split('%') as List
	row[12] = row[12].grep {it != '0'}
	
	//println row
	idCargo++
	idEntrada++
	def mapaDatos = [
		idEntrada: idEntrada,
		idCargo: idCargo,
		idCliente: getIdCliente(row[0]),
		codUsuarioCliente: row[0],
		nombreCliente: row[1],
		apPaternoCliente: row[2],
		apMaternoCliente: row[3],
		anioConstancia: row[4],
		numConstancia: row[5],
		idProducto: getIdArticulo(row[6]),
		nomCortoProducto: row[6],
		tipoCalculoCargo: row[6] == 'AGUACATE'? 'POR_PRESENTACION':'POR_UNIDAD',
		idServicioBase: row[7] != NULL? getIdArticulo(row[7]):row[7],
		nomCortoServicioBase: row[7],
		pesoBruto: row[8] != NULL? row[8]:0,
		idBodega: row[9] != NULL?getIdBodega(row[9]):row[9],
		codUsuarioBodega: row[9],
		valorMercadoKilo: row[10],
		totalEmpaques: row[11] != NULL? row[11]:0,
		numEmpaquesPorTipo: row[12] != [NULL]? row[12]:[],
		idsEmpaques: row[13] != [NULL]? getIdsEmpaques(row[13]):[],
		idEmpaque: row[13] != [NULL] && row[13].size == 1? getIdsEmpaques(row[13])[0]:NULL,
		codUsuarioEmpaques: row[13],
		idUltCargoServicioAlmacenaje: row[19] == 'ALMACENAJE'? idCargo:NULL,
		nomCortoUltServicioAlmacenaje: row[14], //<-- No se toma en cuenta
		fechaEntrada: row[15],
		estatusConstancia: row[16],
		idSerie: idSerie,
		temperatura: row[17],
		entradaVencida: row[18],
		idUnidad: row[16] == 'SIN_CARGOS' ? NULL: getIdUnidad(1),
	
		//Datos para el cargo sumarizado
		tipoServicioCargado: row[19],
		montoCargoTotalizado: row[20] != null && row[20] != NULL? row[20]:0,
		fechaInicioUltCargo: row[21] != null? row[21]:NULL,
		fechaFinUltCargo: row[22] != null? row[22]:NULL,
		fechaCargo: row[23] != null && row[23] != NULL? "'"+row[23]+"'":NULL,
		idServicioCargado: row[24] != null? getIdArticulo(row[24]):row[24],
		nomCortoServicioCargado: row[24],
		estatusCargo: 'ACTIVO',
		tipoCargo: row[19] == 'ALMACENAJE'? "'AP'":NULL
	]
}

datos.each {e ->
	println e
	println e.idUltCargoServicioAlmacenaje
	def insertEntradaAlm = """
insert into almacenaje.m_almacenaje_entrada values 
(${e.idEntrada},${e.pesoBruto},0,NULL,'${e.fechaEntrada}',${e.totalEmpaques},0,
${e.valorMercadoKilo},${e.idProducto},${e.idBodega},${e.idCliente},55,${e.idEmpaque},
${e.idServicioBase},${e.idUnidad},${e.numConstancia},
'${e.estatusConstancia}','${e.tipoCalculoCargo}',${e.pesoBruto},
${e.totalEmpaques},${e.valorMercadoKilo},'MIGRADA',${e.idUltCargoServicioAlmacenaje},NULL,
${e.idSerie},${e.temperatura},${e.entradaVencida});
            """
	
	println insertEntradaAlm
	salida << insertEntradaAlm
	
	if(e.idEmpaque == NULL) {
		e.idsEmpaques.eachWithIndex { val, idx ->
			def insertDetalleEntrada = "insert into almacenaje.e_m_almacenaje_entrada_m_catalogo_pre values (${e.idEntrada},${e.numEmpaquesPorTipo[idx]},${val});"
			//println insertDetalleEntrada
			salida << insertDetalleEntrada
		}
	}
	
	if(e.estatusConstancia != 'SIN_CARGOS'){
				
		if(e.montoCargoTotalizado == 0.toString()){
			e.estatusCargo = 'PAGADO'
		}
		e.diasCargo = NULL
		if(e.tipoServicioCargado == 'ALMACENAJE'){
			use(groovy.time.TimeCategory) {
				def d1 = new Date().parse("yyyy/MM/dd",e.fechaFinUltCargo)
				def d2 = new Date().parse("yyyy/MM/dd",e.fechaInicioUltCargo)
				def duracion = d1-d2
				
				e.fechaFinUltCargo = "'"+e.fechaFinUltCargo+"'"
				e.fechaInicioUltCargo = "'"+e.fechaInicioUltCargo+"'"
				e.diasCargo = duracion.days
			}
		}
		println e.idCargo
		def insertCargoAlm = """
insert into almacenaje.m_cargo_almacenaje values
('${e.tipoServicioCargado}',${e.idCargo},${e.montoCargoTotalizado},
0,${e.diasCargo},${e.fechaFinUltCargo},${e.fechaInicioUltCargo},${e.fechaCargo},${e.idEntrada},
${e.idServicioCargado},${e.pesoBruto},${e.totalEmpaques},NULL,'${e.estatusCargo}',0,
1,16,${e.tipoCargo},NULL,NULL,NULL,NULL);
	"""
	
		println insertCargoAlm
		salida << insertCargoAlm
	}
	println "----"
}

salida << "COMMIT;"
escribirArchivo "/home/pi/tmp/salida_almacenaje.sql", salida
