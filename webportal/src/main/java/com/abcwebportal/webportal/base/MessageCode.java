package com.abcwebportal.webportal.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * This will hold the Entire application exception related message code.
 *
 */
public class MessageCode {
	public static MessageCode PORTAL001InteractionException;
	public static MessageCode PORTAL002EerrorWithParams;
	public static MessageCode PORTAL003Wwarning;
	public static MessageCode PORTAL004IinfoWithParams;

	public static MessageCode PORATG001UserNotFound;
	public static MessageCode PORTALG002UserExist;
	public static MessageCode PORTALG003AuthinticationError;
	public static MessageCode PORTALG004InvalidCredential;

	public static Map<MessageCode, String> allCodes = new HashMap<MessageCode, String>();

	private String name;

	public MessageCode(String name) {
		this.name = name;
		addToList(this);
	}

	/**
	 * 
	 * <P>
	 * To add the message code into the existing list
	 * </p>
	 * 
	 * @param messageCode
	 *            - message code to add to the existing list
	 * @throws RuntimeException
	 *             if message code already presents
	 */

	private static synchronized void addToList(MessageCode messageCode) {
		String id = messageCode.getName().substring(0, 10);
		if (allCodes.get(id) != null) {
			throw new RuntimeException("The message code '" + id + "' already exists.");
		}
		allCodes.put(messageCode, id);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Name:" + getName();
	}

	/**
	 * <p>
	 * It wil get called for initialization of this class
	 * </p>
	 * 
	 * @param messageCodeClass
	 */
	public static void init(Class<? extends MessageCode> messageCodeClass) {
		MessageCode.allCodes.put(PORTALG002UserExist, " Username is not avilable, please choose another ");

		MessageCode.allCodes.put(PORATG001UserNotFound, " User Not Found Exception ");
		allCodes.put(PORTAL001InteractionException, " internal server Error");
		allCodes.put(PORTALG003AuthinticationError, " Could not set user authentication in security context ");
		allCodes.put(PORTALG004InvalidCredential, "Invalid Credentials please try again !!");

		Field[] fields = messageCodeClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (Modifier.isPublic(fields[i].getModifiers()) && Modifier.isStatic(fields[i].getModifiers())
					&& MessageCode.class.isAssignableFrom(fields[i].getType())) {
				try {
					Constructor<?> ctor = fields[i].getType().getConstructor(new Class[] { String.class });
					Object newMessageCode = ctor.newInstance(new Object[] { fields[i].getName() });
					fields[i].set(null, newMessageCode);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
