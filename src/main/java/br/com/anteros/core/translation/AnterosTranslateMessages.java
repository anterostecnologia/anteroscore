package br.com.anteros.core.translation;

import java.util.HashMap;
import java.util.Map;

public class AnterosTranslateMessages implements TranslateMessage {

	private final Map<String, String> messages = new HashMap<String, String>();

	public AnterosTranslateMessages() {
		messages.put("ToStringVisitor.operation_unknown", "Opera\u00e7\u00e3o desconhecida com estes argumentos {0}");

		messages.put("NamedParameterParserResult.toString", "SQL-> {0} Par\u00e2metros: {1}");

		messages.put("LoggerProvider.not_configured",
				"N\u00e3o foi poss\u00edvel encontrar a propriedade para o LoggerProvider no XML de configura\u00e7\u00e3o\u002e Erro: {0}");

		messages.put("EntityCache.entity", "Entidade {0}");

		messages.put("CommandSQL.toString", "sql= {0} , par\u00e2metros={1} , nomeTabelaAlvo={2}");

		messages.put("MySQLDialect.sequenceException", "{0} n\u00e3o suporta sequences.");

		messages.put("H2Dialect.showSql", "Sql-> {0} ## {1}");
		messages.put("H2Dialect.showSql1", "      Input parameters-> ## {0}");
		messages.put("H2Dialect.showSql2", "      Output parameters-> ## {0}");

		messages.put("EventSetDescriptor.IntrospectionException", "M\u00e9todo {0} deve ter argumento {1}");
		messages.put("EventSetDescriptor.IntrospectionException2", "M\u00e9todo {0} n\u00e3o encontrado na classe {1}");

		messages.put("IndexedPropertyDescriptor.IntrospectionException", "M\u00e9todo {0} n\u00e3o encontrado");
	}

	public String getMessage(String key) {
		return messages.get(key);
	}

}
