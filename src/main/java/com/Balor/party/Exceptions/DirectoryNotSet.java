/************************************************************************
 * This file is part of GiftPost.									
 *																		
 * GiftPost is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by	
 * the Free Software Foundation, either version 3 of the License, or		
 * (at your option) any later version.									
 *																		
 * GiftPost is distributed in the hope that it will be useful,	
 * but WITHOUT ANY WARRANTY; without even the implied warranty of		
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the			
 * GNU General Public License for more details.							
 *																		
 * You should have received a copy of the GNU General Public License
 * along with GiftPost.  If not, see <http://www.gnu.org/licenses/>.
 ************************************************************************/
package com.Balor.party.Exceptions;

/**
 * @author Balor (aka Antoine Aflalo)
 *
 */
public class DirectoryNotSet extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1049610426056309144L;

	/**
	 * 
	 */
	public DirectoryNotSet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public DirectoryNotSet(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public DirectoryNotSet(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DirectoryNotSet(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
