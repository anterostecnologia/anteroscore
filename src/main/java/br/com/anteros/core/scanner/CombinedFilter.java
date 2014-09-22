package br.com.anteros.core.scanner;

import java.util.HashSet;
import java.util.Set;

/**
 * Combined filter is a bag of filters. Although the {@link ClassPathScanner} accepts multiple 
 * filters as parameters, its default joins those filters with 'and' logical operator. This filter
 * is required for such cases that you need multiple filter to be joined with logical 'or' operator. 
 *
 */
public class CombinedFilter implements ClassPathResourceFilter {

	private boolean combineWithOr = true;
	private Set<ClassPathResourceFilter> filters = new HashSet<ClassPathResourceFilter>();
	
 
	public boolean accept(Object subject) {
		for(ClassPathResourceFilter filter:filters){
			if(! filter.filterable(subject)) continue;
			if(combineWithOr){
				if (filter.accept(subject)) return true;
			}else{
				if(!filter.accept(subject)) return false;
			}	
		}
		return ! combineWithOr;
	}

 
	public boolean filterable(Object subject) {
		for(ClassPathResourceFilter filter:filters){
			if(filter.filterable(subject)){
				return true;
			}
		}
		return false;
	}

	/*
	 * Builders
	 */
	/**
	 * Combines the given filters with the logical 'or' operator.
	 * This is the default behavior of this filter
	 * @return this
	 */
	public CombinedFilter combineWithOr(){
		combineWithOr = true;
		return this;
	}
	/**
	 * Combines the given filters with the logical 'and' operator
	 * @return this
	 */
	public CombinedFilter combineWithAnd(){
		combineWithOr = false;
		return this;
	}
	
	/**
	 * Appends another filter to this filter
	 * @param filter that need to be combined within this 
	 * @return this
	 */
	public CombinedFilter appendFilter(ClassPathResourceFilter  filter){
		filters.add(filter);
		return this;
	}
	
	
	@Override
	public String toString() {
		return (combineWithOr ? "OR:" : "AND:")+filters.toString();
	}
}
