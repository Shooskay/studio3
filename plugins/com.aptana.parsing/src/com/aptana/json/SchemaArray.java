/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.json;

/**
 * SchemaArray
 */
public class SchemaArray implements State
{
	private Type _elementType;
	private boolean _inArray;

	/**
	 * SchemaArray
	 * 
	 * @param elementType
	 */
	public SchemaArray(Type elementType)
	{
		this._elementType = elementType;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aptana.json.State#enter()
	 */
	public void enter()
	{
		this._inArray = false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aptana.json.State#exit()
	 */
	public void exit()
	{
	}

	/**
	 * getElementType
	 * 
	 * @return
	 */
	public Type getElementType()
	{
		return this._elementType;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aptana.json.State#transition(com.aptana.json.Context, com.aptana.json.EventType, java.lang.Object)
	 */
	public void transition(Context context, EventType event, Object value)
	{
		switch (event)
		{
			case START_ARRAY:
				if (this._inArray)
				{
					throw new IllegalStateException();
				}

				this._inArray = true;

				// Push element type into current context. Note that processing of that type will automatically remove
				// itself from the stack
				context.pushType(this._elementType);
				break;

			case END_ARRAY:
				if (this._inArray == false)
				{
					throw new IllegalStateException();
				}

				this._inArray = false;
				
				// Remove this type from the current context
				context.popType();
				break;

			default:
				throw new IllegalStateException();
		}
	}
}
