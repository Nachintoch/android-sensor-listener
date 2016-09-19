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
 * Escucha un sensor de presi&oacute;n.
 * @author <a href="mailto:manuelignacio_castillolpez@yahoo.com">nacintoch</a>
 * @version 1.0, enero 2013.
 */
public abstract class PressureSensorListener extends NachintochSensorListener {
	
	// atributos de clase
	
	/**
	 * Presi&oacute;n atmosf&eacute;rica registrada.
	 */
	protected float atmosphericPressure;
	
	// métodos constructores
	
	/**
	 * Constructor por omisi&oacute;n.
	 */
	public PressureSensorListener(){
		super(Sensor.TYPE_PRESSURE);
	}//constrctor por omisión
	
	/**
	 * Construye un observador de man&oacute;metro <i>nachintoch</i> con el
	 * <tt>TextView</tt> y actividad dados.
	 * @param activity - Una actividad de la aplicaci&oacute;n.
	 * @param view - La pantalla donde se mostrar&aacute;n los datos del sensor.
	 * @see NachintochSensorListener#NachintochSensorListener(android.widget.TextView, android.app.Activity, int)
	 */
	public PressureSensorListener(TextView view, Activity activity){
		super(view, activity, Sensor.TYPE_PRESSURE);
	}//constructor con característica de gui update y actividad
	
	/**
	 * Construye un observador de man&oacute;metro <i>nachintoch</i> con el
	 * <tt>TextView</tt> y servicio dados.
	 * @param service - Un servicio de la aplicaci&oacute;n.
	 * @param view - La pantalla donde se mostrar&aacute;n los datos del sensor.
	 * @see NachintochSensorListener#NachintochSensorListener(android.widget.TextView, android.app.Service, int)
	 */
	public PressureSensorListener(TextView view, Service service){
		super(view, service, Sensor.TYPE_PRESSURE);
	}//constructor con característica de gui update y servicio
	
	// métodos de acceso
	
	/**
	 * Indica la presi&oacute;n atmosf&eacute;rica registrada.
	 * @return float - La presi&oacute;n atmosf&eacute;rica.
	 */
	public float getAtmosphericPressure(){
		return atmosphericPressure;
	}//getAmbientLight
	
	// métodos de implementación
	
	@Override
	public void onSensorChanged(SensorEvent event){
		super.retrieveData(event.accuracy, event.timestamp, event.values);
		atmosphericPressure = event.values[0];
		advertize();
		notificate();
	}//onSensorChanged
	
	@Override
	public void advertize(){
		if(screen != null)
		screen.setText(getTypeString() +"\n\tPresici\u00F3n: "+
				getAccuracyString() +"\n\tPresi\u00F3n atmosf\u00E9rica: "
				+atmosphericPressure);
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
	public static PressureSensorListener getInstance(){
		return new PressureSensorListener() {
			public void onAccuracyChanged(Sensor sensor, int accuracy){
				this.accuracy = accuracy;
				didAccuracyChanged = true;
			}
			protected void notificate(){}
		};
	}//getInstance

}//PressureSensorListener
