/*
 * Apache License
 *
 * Copyright (c) 2019 andrescg2sj
 *
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.sj.punidos.crminer.sectorizer;

import java.awt.Point;
import java.awt.Rectangle;

//import org.apache.pdfbox.pdmodel.font.PDFont;

public class GraphicString {

	String text;
	Rectangle bounds;
	
	public GraphicString(String t, Rectangle b) {
		text = t;
		bounds = b;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public String getText() {
		return text;
	}

	public Point getPosition() {
		if(bounds == null) {
			return null;
		}
		return bounds.getLocation();
	}
}
