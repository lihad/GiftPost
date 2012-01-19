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
package com.Balor.Tools.Configuration.File;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
class YamlConstructor extends Constructor {
	private HashMap<String, Class<?>> classMap = new HashMap<String, Class<?>>();

	public YamlConstructor(Class<? extends Object> theRoot) {
		super(theRoot);
	}

	/**
	 * 
	 */
	public YamlConstructor() {
		super();
		this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
	}

	public void addClassInfo(Class<? extends Object> c) {
		classMap.put(c.getName(), c);
	}

	/*
	 * This is a modified version of the Constructor. Rather than using a class
	 * loader to get external classes, they are already predefined above. This
	 * approach works similar to the typeTags structure in the original
	 * constructor, except that class information is pre-populated during
	 * initialization rather than runtime.
	 * 
	 * @see org.yaml.snakeyaml.constructor.Constructor#getClassForName(org.yaml
	 * .snakeyaml.nodes.Node)
	 */
	@Override
	protected Class<?> getClassForName(String name) throws ClassNotFoundException {
		Class<?> cl = classMap.get(name);
		if (cl == null)
			return super.getClassForName(name);
		else
			return cl;
	}

	/**
	 * Check if the class is registered
	 * 
	 * @param c
	 * @return
	 */
	public boolean isClassRegistered(Class<? extends Object> c) {
		return classMap.containsKey(c.getName());
	}


	private class ConstructCustomObject extends ConstructYamlMap {
		@SuppressWarnings("unchecked")
		@Override
		public Object construct(Node node) {
			if (node.isTwoStepsConstruction()) {
				throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
			}

			Map<Object, Object> raw = (Map<Object, Object>) super.construct(node);

			if (raw.containsKey(ConfigurationSerialization.SERIALIZED_TYPE_KEY)) {
				Map<String, Object> typed = new LinkedHashMap<String, Object>(raw.size());
				for (Map.Entry<Object, Object> entry : raw.entrySet()) {
					typed.put(entry.getKey().toString(), entry.getValue());
				}

				try {
					return ConfigurationSerialization.deserializeObject(typed);
				} catch (IllegalArgumentException ex) {
					throw new YAMLException("Could not deserialize object", ex);
				}
			}

			return raw;
		}

		@Override
		public void construct2ndStep(Node node, Object object) {
			throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
		}
	}
}
