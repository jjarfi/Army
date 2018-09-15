/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package En.Enscrypt;

import java.util.BitSet;

/**
 *
 * @author pacevil
 */
public class YBitSet extends BitSet {
	
	public YBitSet( int size ) {
		super(size);
	}
	
	
	public char[] bitSetToCharArray(int numberOfBitsFromLeft ) {
		
		char theBitSet[] = new char[numberOfBitsFromLeft];
		
		for ( int i = 0; i < numberOfBitsFromLeft; i++ ) {
			if ( super.get(i) )
			theBitSet[i] = '1';
			else
			theBitSet[i] = '0';
		}
		
		return theBitSet;
	}
	
	public void initializeAccordingToCharArray( char a[] ) {
		
		for ( int i = 0; i < a.length; i++ ) {
			if ( a[i] == '1' )
			this.set(i);
			else if ( a[i] == '0' )
			this.clear(i);
		}
	}
	
	
	public YBitSet LS_1(int numberOfBitsFromLeft) {
		
		char oldBitSet[] = this.bitSetToCharArray(numberOfBitsFromLeft);
		char newBitSet[] = new char[oldBitSet.length];
		
		newBitSet[ newBitSet.length - 1 ] = oldBitSet[0];
		
		for ( int i = 0; i < newBitSet.length - 1; i++ ) {
			newBitSet[i] = oldBitSet[i + 1];
		}
		
		YBitSet shiftedSet = new YBitSet(newBitSet.length);
		shiftedSet.initializeAccordingToCharArray(newBitSet);
		
		return shiftedSet;
	}
	
	public YBitSet LS_2( int numberOfBitsFromLeft ) {
		
		YBitSet firstShift = new YBitSet( numberOfBitsFromLeft);
		YBitSet secondShift = new YBitSet(numberOfBitsFromLeft);
		
		firstShift = this.LS_1(numberOfBitsFromLeft);
		secondShift = firstShift.LS_1(numberOfBitsFromLeft);
		
		return secondShift;
	}
}
	
	
		
		
		
		
	