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

/**
 * Biblioteca con clases que implementan la interf&aacute;z
 * <tt>android.hardware.SensorEventListener</tt> pre-especializando el m&eacute;todo
 * <tt>onSensorChanged(android.hardware.SensorEvent)</tt> para cada tipo de sensor
 * disponible hasta el nivel 17 de API de Android.<br/>
 * Los datos que contienen las instancias son instant&aacute;neos; es decir,
 * corresponden al &uacute;ltimo evento ocurrido con el sensor en cuesti&oacute;n
 * y se actualizan cada vez que ocurre uno.<p/>
 * 
 * No se espera de la implementaci&oacute;n del m&eacute;todo
 * <tt>onAccuracyChanged(android.harware.Sensor)</tt> m&aacute;s que un algoritmo
 * sin instrucciones.<p/>
 * Nachintoch Sensor Listeners Library.<br/>
 * Copyright 2012, 2013, Manuel Castillo.<p/>
 * Nachintoch Sensor Listeners Library is free software: you can redistribute it and/or modify<br/>
 *  it under the terms of the Lesser GNU General Public License as published by<br/>
 *  the Free Software Foundation, either version 3 of the License, or<br/>
 *  (at your option) any later version.<p/>
 *
 *  Nachintoch Sensor Listeners Library is distributed in the hope that it will be useful,<br/>
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of<br/>
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the<br/>
 *  Lesser GNU General Public License for more details.<p/>
 *
 *  You should have received a copy of the Lesser GNU General Public License<br/>
 *  along with Nachintoch Sensor Listeners Library.  If not, see <http://www.gnu.org/licenses/>.
 * @author <a href="mailto:teshanatsch@gmail.com">nacintoch</a>
 * @since Diciembre 2012.
 * @version 1.1, Enero 2013.
 */
package com.nachintoch.sensorlistenerslibrary;