package br.com.anteros.core.utils;


public class AnterosCoreTranslate extends AbstractCoreTranslate {
	
	public AnterosCoreTranslate(String messageBundleName) {
		super(messageBundleName);
	}

	private static AnterosCoreTranslate translate;
	
	public static AnterosCoreTranslate getInstance(){
		if (translate==null){
			translate = new AnterosCoreTranslate("anteroscore_messages");
		}
		return translate;
	}
	
	
}
