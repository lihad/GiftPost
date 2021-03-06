/************************************************************************
 * This file is part of AdminCmd.
 *
 * AdminCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AdminCmd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AdminCmd.  If not, see <http://www.gnu.org/licenses/>.
 ************************************************************************/
package VirtualChest.Manager.Permissions;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class NoPermissionsPlugin extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 2193584431299840282L;

	/**
	 *
	 */
	public NoPermissionsPlugin(){
	}

	/**
	 * @param s
	 */
	public NoPermissionsPlugin(String s) {
		super(s);
	}

	/**
	 * @param cause
	 */
	public NoPermissionsPlugin(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoPermissionsPlugin(String message, Throwable cause) {
		super(message, cause);
	}
}
