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
import android.widget.TextView;

/**
 * Escucha un sensor de proximidad.
 * @author <a href="mailto:manuelignacio_castillolpez@yahoo.com">nacintoch</a>
 * @version 1.0, enero 2013.
 */
public abstract class ProximitySensorListener extends NachintochSensorListener {
	
	// atributos de clase
	
	/**
	 * Proximidad medida.
	 */
	protected float proximity;
	
	// métodos constructores
	
	/**
	 * Constructor por omisi&oacute;n.
	 */
	public ProximitySensorListener(){
		super(Sensor.TYPE_PROXIMITY);
	}//constructor por omisión
	
	/**
	 * Construye un observador de sensor de proximidad <i>nachintoch</i> con el
	 * <tt>TextView</tt> y actividad dados.
	 * @param activity - Una actividad de la aplicaci&oacute;n.
	 * @param view - La pantalla donde se mostrar&aacute;n los datos del sensor.
	 * @see NachintochSensorListener#NachintochSensorListener(android.widget.TextView, android.app.Activity, int)
	 */
	public ProximitySensorListener(TextView view, Activity activity){
		super(view, activity, Sensor.TYPE_PROXIMITY);
	}//constructor con característica de gui update y actividad
	
	/**
	 * Construye un observador de sensor de proximidad <i>nachintoch</i> con el
	 * <tt>TextView</tt> y servicio dados.
	 * @param service - Un servicio de la aplicaci&oacute;n.
	 * @param view - La pantalla donde se mostrar&aacute;n los datos del sensor.
	 * @see NachintochSensorListener#NachintochSensorListener(android.widget.TextView, android.app.Service, int)
	 */
	public ProximitySensorListener(TextView view, Service service){
		super(view, service, Sensor.TYPE_PROXIMITY);
	}//constructor con característica de gui update y servicio
	
	// métodos de acceso
	
	/**
	 * Indica la proximidad en cent&iacute;metros medida.
	 * @return float - La proximidad medida.
	 */
	public float getProximity(){
		return proximity;
	}//getAmbientLight
	
	// métodos de implementación
	
	@Override
	public void onSensorChanged(SensorEvent event){
		super.retrieveData(event.accuracy, event.timestamp, event.values);
		proximity = event.values[0];
		advertize();
		notificate();
	}//onSensorChanged
	
	@Override
	public void advertize(){
		if(screen != null)
		screen.setText(getTypeString() +"\n\tPresici\u00F3n: "+
				getAccuracyString() +"\n\tProximidad de algo: " +proximity);
	}//advertize
	
	// métodos estáticos
	
	/**
	 * Da una instancia de &eacute;sta clase con una implementaci&oacute;n
	 * del m&eacute;todo <tt><b>onAccuracyChanged</b>(android.hardware.<b>Sensor</b>,
	 * <b>int</b>)</tt> que consiste en actualizar la informaci&oacute;n de
	 * presici&oacute;n registrada del sensor y tomar nota de que la
	 * presici&oacute;n ha cambiado.<p/>
	 * 
	 * La implementaci&oacute;n de <tt><b>notificate</b></tt> consiste en un
	 * m&eacute;todo vac&iacute;o.
	 * @see NachintochSensorListener
	 * @see android.hardware.SensorEventListener
	 * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
	 * @return PreassureSensorListener - Una instancia de &eacute;sta clase.
	 */
	public static ProximitySensorListener getInstance(){
		return new ProximitySensorListener() {
			public void onAccuracyChanged(Sensor sensor, int accuracy){
				this.accuracy = accuracy;
				didAccuracyChanged = true;
			}
			protected void notificate(){}
		};
	}//getInstance

}//ProximitySensorListener class
