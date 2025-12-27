package combinatorics;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class PermutationImpl_Martinez<E> implements Permutation<E>
{
	private Set<List<E>> cycles;
	private Set<E> domain;
	
	public PermutationImpl_Martinez(Set<List<E>> cycles, Set<E> domain)
	{
		
		this.cycles = new HashSet<List<E>>();
		
		// Checks if cycles is empty and domain is not
		if (cycles == null && domain != null) {
			List<E> singleCycle = new ArrayList<>();
			for (E element: domain) {
				singleCycle.add(element);
				
				this.cycles.add(singleCycle);
				singleCycle.clear();
			}
			
		} else {
		
			List<E> testDomain = new ArrayList<E>();
			
			for(List<E> cycle: cycles) {
				this.cycles.add(cycle);
				
				// Adding to test domain
				for(E element: cycle) {
					
					assert !testDomain.contains(element): "Overlap in Cycles";
					testDomain.add(element);
					
					// Check for if element is missing in domain
					assert domain.contains(element): "Not all elements are in Domain";
				}
			}
		}
		this.domain = domain;
		
	}
	

	public PermutationImpl_Martinez(Set<List<E>> cycles) 
	{
		
		this.cycles = new HashSet<List<E>>();
		this.domain = new HashSet<E>();
		
		for (List<E> cycle: cycles) {
			// Adds cycle to cycles
			this.cycles.add(cycle);
			
			// Adds elements to the domain
			for (E element: cycle) {
				assert !domain.contains(element): "Overlap in Cycles";
				domain.add(element);
			}
		}
		
		// Check for if element is missing in domain
		for (List<E> cycle: cycles) {
			for (E element: cycle) {
				assert domain.contains(element): "Not all elements are in Domain";
			}
		}
	}
	
	@Override
	public E getImage(E e)
	{
		assert getDomain().contains(e): "Element is not in the Domain!";
		
		E imageCandidate = null;
		
		for (List<E> cycle: cycles) {
			for (E element: cycle) {
				if(element == e) {
					if (cycle.indexOf(element) >= cycle.size() -1) {
						// if it is equal to the length
						imageCandidate = cycle.get(0);
					} else {
						
						imageCandidate = cycle.get(cycle.indexOf(element) + 1);
					}
				}
			}
		}
		
		// for single cycle elements if not listed
		if (imageCandidate == null) {
			imageCandidate = e;
		}
		
		return imageCandidate;
	}

	@Override
	public E getPreImage(E e)
	{
		assert getDomain().contains(e): "Element is not in the Domain!";
		
		E preImageCandidate = null;
		
		for (List<E> cycle: cycles) {
			for (E element: cycle) {
				if(element == e) {
					if (cycle.indexOf(element) <= 0) {
						// if it is equal to 0 wrap around
						preImageCandidate = cycle.get(cycle.size() -1);
					} else {
						
						preImageCandidate = cycle.get(cycle.indexOf(element) - 1);
					}
				}
			}
		}
		
		// for single cycle elements if not listed
		if (preImageCandidate == null) {
			preImageCandidate = e;
		}
		
		return preImageCandidate;
	}

	@Override
	public Set<E> getDomain()
	{
		return new HashSet<>(domain);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		boolean areSameType = (obj != null && Permutation.class.isAssignableFrom(obj.getClass()));
		
		boolean foundDisagreement = !areSameType;
		
		if(areSameType)
		{
			//now I know this and obj are both Person instances
			Permutation<E> other = (Permutation<E>)obj;
			boolean domainsAgree = (this.getDomain().equals(other.getDomain()));
			boolean imagesAgree = true;
			for (E element: domain) {
				if (this.getImage(element) != other.getImage(element)) {
					imagesAgree = false;
					break;
				}
			}
			
			foundDisagreement = !domainsAgree || !imagesAgree;
		}
		
		boolean areEqual = (!foundDisagreement);
		return areEqual;
	}
	
}