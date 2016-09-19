package com.nachintoch.sensorlistenerslibrary;

/*
 * Nachintoch Sensor Listeners Library.
 * Copyright 2012, 2013, Manuel Castillo.
 * Nachintoch Listeners Library is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Nachintoch Sensor Listeners Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the Lesser GNU General Public License
 * along with Nachintoch Sensor Listeners Library.  If not, see <http://www.gnu.org/licenses/>.
 */

import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;

/**
 * Escucha un sensor.<p/>
 * 
 * Sirve como base para todos los dem&aacute;s receptores de eventos de sensor
 * <i>nachintoch</i>.<p/>
 * 
 * En sus implementaci&oacute;n no se espera encontrar clases no abstractas;
 * pues se pretende que el m&eacute;todo
 * <tt><b>onAccuracyChanged</b>(android.hardware.<b>Sensor</b>, <b>int</b>)</tt>
 * permanezca como abstracto para que se pueda crear una implementaci&oacute;n
 * personalizada.<p/>
 *  
 * Sin embargo; se incluye un m&eacute;todo est&aacute;tico <i>getInstance()</i>
 * en las clases descendietes para poder usarlas.<p/>
 * 
 * Permite actualizar autom&aacute;ticamente la pantalla cuando se registran
 * cambios en la pantalla cuando as&iacute; sea solicitado.<p/>
 * 
 * Puede pasarsele la referencia a un servicio o actividad (
 * <tt>android.app.Activity</tt>, <tt>android.app.Service</tt>) y enviar
 * se&ntilde;ales intent notificando el suceso. Si no se le da la referencia,
 * no se envian las se&ntilde;ales.<br/>
 * Tome en cuenta que s&oacute;lo el servicio o la actividad est&aacute;n
 * en servicio uno a la vez. Ambos pueden ser referencias nulas, per nunca ambos
 * son referencias a objetos en memoria. Si se a ajustado alguno como referencia
 * a un objeto en memoria; al ajustar el otro como otra referencia, se pierde
 * la referencia del primero haciendose nula.<p/>
 * 
 * El prop&oacute;sito de &eacute;sta biblioteca es facilitar el uso de receptores
 * de eventos de sensores.
 * @see android.hardware.SensorEventListener
 * @see android.hardware.Sensor
 * @see android.hardware.SensorEvent
 * @author <a href="manuelignacio_castillolpez@yahoo.com">nacintoch</a>
 * @version 1.1, Enero 2013.
 */
public abstract class NachintochSensorListener implements SensorEventListener {

	// Atributos de clase
	
	/**
	 * La presici&oacute;n del evento.
	 */
	protected int accuracy;
	
	/**
	 * El momento en el que ocurrio el evento en nanosegundos.
	 */
	protected long eventTime;
	
	/**
	 * Indica si en alg&uacute;n momento cambi&oacute; la presici&oacute;n del
	 * sensor desde que se comenz&oacute; su monitorizaci&oacute;n.
	 */
	protected boolean didAccuracyChanged;
	
	/**
	 * Contiene los valores registrados por el sensor.
	 */
	protected float[] lectures;

	/*
	 * Referencia a un objeto de tipo <tt>Handler</tt> para actualizar la pantalla
	 * cada vez que ocurran cambios en los datos del sensor de acuerdo al
	 * m&eacute;todo <tt>Run()</tt> del objeto runnable que se le pase como
	 * par&aacute;metro al <tt>Handler</tt>.<p/>
	 * 
	 * &Eacute;sta caracter&iacute;stica puede no funcionar si <tt>doRefresh</tt>
	 * es <tt>false</tt>.
	 * @see android.os.Handler
	 * @see #doRefresh
	 *
	protected Handler guiUpdater;*/
	
	/*
	 * Referencia a un objeto de tipo <tt>Runnable</tt> con las instrucciones
	 * para actualizar la pantalla.
	 * @see #guiUpdater
	 *
	protected Runnable thread;*/
	
	/**
	 * Indica si cuando se registren cambios en los datos del sensor, se debe
	 * actualizar la pantalla.
	 * 
	 * @see #guiUpdater
	 */
	protected boolean doRefresh;
	
	/**
	 * Referencia a una actividad.
	 */
	protected Activity activity;
	
	/**
	 * Referencia a un servicio.
	 */
	protected Service service;

	/*
	 * Tiempo de actualizaci&oacute;n de la pantalla para la
	 * caracter&iacute;stica de refrescar la pantalla.
	 *
	protected long refreshTime;*/
	
	/*
	 * Encargado manejar la actalizaci&oacute;n de la pantalla basada en el
	 * tiempo de actualizaci&oacute;n.
	 * @see #refreshTime
	 *
	protected Timer updateTimer;*/
	
	/**
	 * Referencia a la pantalla del programa para mostrar los datos leidos por el
	 * sensor; cuando se solicite usar la caracter&iacute;stica de refrescar
	 * autom&aacute;ticamente la pantalla.
	 * @see #doRefresh
	 */
	protected TextView screen;
	
	/**
	 * Indica que es posible actualizar la pantalla.
	 */
	public static final byte SCREEN_UPDATING_AVAILABLE = 0;
	
	/**
	 * Indica que no es posible actualizar la pantalla debido a que no se
	 * ha hecho la petici&oacute;n
	 */
	public static final byte SCREEN_UPDATING_NOT_REQUESTED = 1;
	
	/**
	 * Indica que no es posible actualizar la pantalla
	 * debido a que <tt>screen</tt> es una referencia nula.
	 */
	public static final byte NULL_SCREEN = 2;

	/*
	 * Indica que no es posible actualizar la pantalla
	 * debido a que <tt>thread</tt> es una referencia nula.
	 *
	public static final byte NULL_THREAD = 3;

	/**
	 * Indica que no es posible actualizar la pantalla
	 * debido a que no se ha hecho la petici&oacute;n y
	 * <tt>screen</tt> es una referencia nula.
	 *
	public static final byte SCREEN_UPDATING_NOT_REQUESTED_NULL_SCREEN = 4;
	
	/*
	 * Indica que no es posible actualizar la pantalla
	 * debido a que no se ha hecho la petici&oacute;n y
	 * <tt>thread</tt> es una referencia nula.
	 *
	public static final byte SCREEN_UPDATING_NOT_REQUESTED_NULL_THREAD = 5;
	
	/**
	 * Indica que no es posible actualizar la pantalla
	 * debido a que tanto <tt>guiUpdater</tt> como
	 * <tt>thread</tt> son referencias nulas.
	 *
	public static final byte NULL_UPDATER_NULL_THREAD = 6;*/

	/**
	 * Indica que no es posible actualizar la pantalla
	 * debido a que no se ha hecho la peticion y
	 * <tt>screen</tt> es una referencia nula.
	 */
	public static final byte SCREEN_UPDATING_UNAVAILABLE = 7;
	
	/**
	 * Tipo del sensor observado.
	 */
	public final int SENSOR_TYPE;
	
	// métodos constructores
	
	/**
	 * Método constructor por omisi&oacute;n.
	 * @param type - El identificador del tipo de sensor.
	 * @see android.hardware.Sensor*//*<p/>
	 * Ajusta el tiempo de actualizaci&oacute;n de la pantalla a 500
	 * microsegundos. */ /*Asigna a <tt>accuracy</tt> el
	 * valor que indica que los datos obtenidos por el sensor no son de fiar.
	 * @see #accuracy
	 * @see android.hardware.SensorManager#SENSOR_STATUS_UNRELIABLE
	 */
	public NachintochSensorListener(int type){
		//accuracy = SensorManager.SENSOR_STATUS_UNRELIABLE;
		//refreshTime = 500;
		//updateTimer = new Timer("guiUpdater");
		SENSOR_TYPE = type;
	}//constructor por omisión
	
	/**
	 * Construye un observador de sensor <i>nachintoch</i> con el
	 * <tt>View</tt> y actividad dados.
	 * @param view - Referencia a la pantalla donde se mostrar&aacute;n los datos
	 * leidos por el sensor.
	 * @param activity - Una actividad de la aplicaci&oacute;n.
	 * @param type - El tipo del sesnor.
	 */
	public NachintochSensorListener(TextView view, Activity activity, int type){
		//guiUpdater = handler;
		//this.thread = thread;
		//refreshTime = time;
		screen = view;
		this.activity = activity;
		SENSOR_TYPE = type;
		//updateTimer = new Timer("guiUpdater");
	}//constructor con característica de gui update y actividad
	
	/**
	 * Construye un observador de sensor <i>nachintoch</i> con el
	 * <tt>View</tt> y servicio dados.
	 * @param view - Referencia a la pantalla donde se mostrar&aacute;n los datos
	 * del sensor.
	 * @param service - Un servicio de la aplicaci&oacute;n.
	 * @param type - El tipo del sensor.
	 */
	public NachintochSensorListener(TextView view, Service service, int type){
		//guiUpdater = handler;
		//this.thread = thread;
		//refreshTime = time;
		screen = view;
		this.service = service;
		SENSOR_TYPE = type;
	}//constructor con característica de gui update y servicio
	
	// métodos de modificación
	
	/**
	 * Altera el valor de <tt>screen</tt>
	 * @param view - Referencia a un objeto <tt>View</tt>
	 * @see #screen
	 */
	public void setScreen(TextView view){
		screen = view;
	}//setGUIUpdater
	
	/*
	 * Altera el hilo de ejecuci&oacute;n dise&ntilde;ado para refrescar la
	 * pantalla.
	 * @param guiThread - El hilo de ejecuci&oacute;n que debe encargarse de
	 * refrescar la pantalla.
	 * @see android.os.Handler#post(java.lang.Runnable)
	 *
	public void setThread(Runnable guiThread){
		thread = guiThread;
	}//setThread*/
	
	/*
	 * Ajusta el tiempo que pasa entre periodos de actualizaci&oacute;n de la
	 * pantalla.
	 * @see #refreshTime
	 *
	public void setRefreshTime(long time){
		refreshTime = time;
	}//setRefreshTime*/
	
	// métodos de acceso
	
	/**
	 * Indica la presici&oacute;n.
	 * @return int - La presici&oacute;n.
	 */
	public int getAccuracy() {
		return accuracy;
	}//getAccuracy
	
	/**
	 * Indica el momento del evento.
	 * @return long - El momento en el que ocurrio el evento en nanosegundos.
	 */
	public long getEventTime(){
		return eventTime;
	}//getEventTime
	
	// métodos de implementación

	/**
	 * Indica la presici&oacute;n.<p/>
	 * 
	 * La presici&oacute;n se indica explicitamente como cadena.
	 * @return String - La presici&oacute;n.
	 */
	public String getAccuracyString(){
		switch(accuracy){
		case SensorManager.SENSOR_STATUS_UNRELIABLE :
			return "Los datos obtenidos por el sensor no son confiables";
		case SensorManager.SENSOR_STATUS_ACCURACY_LOW :
			return "La presici\u00F3n es baja";
		case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM :
			return "La presici\u00F3n es promedio";
		case SensorManager.SENSOR_STATUS_ACCURACY_HIGH :
			return "La presici\u00F3n es \u00F3ptima";
		default :
			return "La presici\u00F3n es desconocida";
		}//muestra la presición como cadena
	}//getAccuracy
	
	/**
	 * Devuelve como cadena de caracteres el tipo del sensor.
	 */
	@SuppressWarnings("deprecation")
	public String getTypeString(){
		switch(SENSOR_TYPE) {
		case Sensor.TYPE_ACCELEROMETER :
			return "Aceler\u00F3metro";
		case Sensor.TYPE_GRAVITY :
			return "Aceler\u00F3metro gravitacional";
		case Sensor.TYPE_GYROSCOPE :
			return "Giroscopio";
		case Sensor.TYPE_LIGHT :
			return "Sensor fotoel\u00E9ctrico";
		case Sensor.TYPE_LINEAR_ACCELERATION :
			return "Aceler\u00F3metro lineal";
		case Sensor.TYPE_MAGNETIC_FIELD :
			return "Magnet\u00F3metro";
		case Sensor.TYPE_PRESSURE :
			return "Bar\u00F3metro";
		case Sensor.TYPE_PROXIMITY :
			return "Sensor de proximidad";
		case Sensor.TYPE_ROTATION_VECTOR :
			return "Sensor de rotaci\u00F3n";
		case Sensor.TYPE_AMBIENT_TEMPERATURE :
			return "Term\u00F3metro";
		case Sensor.TYPE_RELATIVE_HUMIDITY :
			return "Sensor de humedad";
		case Sensor.TYPE_ORIENTATION :
			return "Sensor de orientaci\u00F3n del dispositivo";
		case Sensor.TYPE_TEMPERATURE :
			return "Term\u00F3metro";
		default:
			return "Sensor no identificado";
		}//identifica o no el tipo de sensor
	}//getTypeString
	
	/**
	 * Ajusta los atributos comunes los eventos de sensor.
	 * @param event - El evento ocurrido.
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 * @see #retrieveData(android.hardware.SensorEvent)
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		retrieveData(event.accuracy, event.timestamp, event.values);
		if(doRefresh && (screen != null) /*&& (thread != null)*/)
			advertize();
		notificate();
	}//onSensorChanged

	/**
	 * Muestra las lecturas como una cadena.
	 * @return String - Una representacion legible de las lecturas del sensor.
	 */
	public String showLectures(){
		String data = "";
		if(lectures != null){
			for(int i = 0; i < lectures.length; i++){
				data += "Datos " +i +":\t";
				data += lectures[i];
				data += "\n";
			}//recupera los datos leidos
			data = data.substring(0, data.length() -2);
		}//si hay datos
		return data;
	}//showLectures
	
	/**
	 * Indica si un objeto <tt>View</tt> es equivalente al que se pasa como
	 * par&aacute;metro.
	 * @see android.os.Handler#equals(java.lang.Object)
	 */
	public boolean handlerEquals(View other){
		return screen.equals(other);
	}//handlerEquals
	
	/*
	 * Indica si un objeto <tt>Runnable</tt> es equvalente al que se pasa como
	 * par&aacute;metro.
	 * @see java.lang.Object#equals(java.lang.Object)
	 *
	public boolean runnableEquals(Runnable other){
		return thread.equals(other);
	}//runnableEquals*/
	
	/**
	 * Hace la peticion de actualizar la pantalla cada vez que se registran
	 * cambios en los datos del sensor.<p/>
	 * 
	 * Para poder actualizar la pantalla, es necesario primero asignar una
	 * referencia a un objeto existente en memoria a <tt>screen</tt>. De otro
	 * modo; aunque se haga la petici&oacute;n de actualizar la pantalla con
	 * &eacute;ste m&eacute;ste m&eacute;todo, no ocurrira tal cosa.
	 * @see #doRefresh
	 */
	public void screenUpdated(){
		doRefresh = true;
	}//screenUpdated
	
	/**
	 * Remueve la peticion de actualizar la pantalla.
	 * @see #doRefresh
	 */
	public void stopScreenUpdating(){
		doRefresh = false;
	}//stopScreenUpdating
	
	/**
	 * Indica si la actualizaci&oacute;n de la pantalla est&aacute; disponible
	 * y el motivo.
	 * @return byte - El estado en el que se encuentra la capacidad de refrescar
	 * la pantalla
	 * @see #SCREEN_UPDATING_AVAILABLE
	 * @see #SCREEN_UPDATING_NOT_REQUESTED
	 * @see #NULL_SCREEN
	 * @see #SCREEN_UPDATING_UNAVAILABLE
	 */
	public byte screenUpdatingState(){
		/*if(!doRefresh && (guiUpdater == null) && (thread == null))
			return SCREEN_UPDATING_UNAVAILABLE;
		if((guiUpdater == null) && (thread == null))
			return NULL_UPDATER_NULL_THREAD;*/
		if(!doRefresh && (screen == null))
			return SCREEN_UPDATING_UNAVAILABLE;
		/*if(!doRefresh && (guiUpdater == null))
			return SCREEN_UPDATING_NOT_REQUESTED_NULL_UPDATER;*/
		if(screen == null)
			return NULL_SCREEN;
		/*if(guiUpdater == null)
			return NULL_GUI_UPDATER;*/
		if(!doRefresh)
			return SCREEN_UPDATING_NOT_REQUESTED;
		return SCREEN_UPDATING_AVAILABLE;
	}//screenUpdatingState
	
	/**
	 * Asigna la actividad dada como contexto.<p/>
	 * 
	 * Note que si el servicio se hab&iacute;a asignado como contexto previamente,
	 * ser&aacute; reemplazado por la actividad.
	 * @param context - La actividad a asignar como contexto.
	 */
	public void setActivityContext(Activity context){
		activity = context;
		if(service != null)
			service = null;
	}//setActivityContext
	
	/**
	 * Asigna el servicio dado como contexto.<p/>
	 * 
	 * Note que si la actividad se hab&iacute;a asignado como contexto
	 * previamente, ser&aacute; reemplazado por el servicio.
	 * @param context - El servicio a asignar como contexto.
	 */
	public void setServiceContext(Service context){
		service = context;
		if(activity != null)
			activity = null;
	}//setServiceContext
	
	// métodos auxiliares
	
	/**
	 * Notifica de alguna manera que acaba de ocurrir un cambio en los datos
	 * del sensor.
	 * @see #onSensorChanger(android.hardware.SensorEvent)
	 */
	protected abstract void notificate();
	
	/**
	 * Recupera los datos leidos del sensor.
	 * @param accuracy - La presici&oacute;n del sensor.
	 * @param timeOcurred - El momento en el que ocurrio el evento.
	 * @param values - Los valores recuperados por el sensor.
	 * @see android.hardware.SensorEvent
	 */
	protected void retrieveData(int accuracy, long timeOcurred, float[] values){
		this.accuracy = accuracy;
		eventTime = timeOcurred;
		lectures = values;
	}//retrieveData
	
	/**
	 * Actualiza la pantalla; de ser posible y notifica de un evento de sensor.
	 * &Eacute;ste m&eacute;todo es llamado autom&aacute;ticamente siempre
	 * que ocurre un evento de sensor.
	 */
	protected void advertize(){
		/*Timer updateTimer = new Timer("guiUpdater");
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run(){
				guiUpdater.post(thread);
			}
		}, 0, refreshTime);*/
		screen.setText(getTypeString() +"\n\tPresici\u00F3n: "+
				getAccuracyString() +"\n\tDatos leidos: " +lectures);
	}//advertize
	
	// métodos estáticos
	
	/**
	 * Da una instancia de &eacute;sta clase con una implementaci&oacute;n
	 * del m&eacute;todo <tt><b>onAccuracyChanged</b>(
	 * android.hardware.<b>Sensor</b>, <b>int</b>)</tt> que consiste en
	 * actualizar la informaci&oacute;n de presici&oacute;n registrada del
	 * sensor y tomar nota de que la presici&oacute;n ha cambiado.<p/>
	 * 
	 * La implementaci&oacute;n de <tt><b>notificate</b></tt> consiste en un
	 * m&eacute;todo vac&iacute;o.
	 * @param type - El tipo del sensor a observar.
	 * @see NachintochSensorListener
	 * @see android.hardware.SensorEventListener
	 * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
	 * @return NachintochSensorListener - Una instancia de &eacute;sta clase.
	 */
	public static NachintochSensorListener getInstance(int type){
		return new NachintochSensorListener(type) {
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy){
				this.accuracy = accuracy;
				didAccuracyChanged = true;
			}
			protected void notificate(){}
		};
	}//getInstance
	
}//NachintochSensorListener class
