/*
    Copyright (C) 2013 Nicola L.C. Talbot
    www.dickimaw-books.com

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/
package com.dickimawbooks.texparserlib.latex;

import java.io.IOException;

import com.dickimawbooks.texparserlib.*;

public class ProvidesFile extends ControlSequence
{
   public ProvidesFile()
   {
      this("ProvidesFile");
   }

   public ProvidesFile(String name)
   {
      super(name);
   }

   public Object clone()
   {
      return new ProvidesFile(getName());
   }

   // All arguments are ignored

   public void process(TeXParser parser, TeXObjectList list)
     throws IOException
   {
      TeXObject options = list.popArg(parser, '[', ']');

      TeXObject name = list.popArg(parser);

      TeXObject version = list.popArg(parser, '[', ']');
   }

   public void process(TeXParser parser)
     throws IOException
   {
      TeXObject options = parser.popNextArg('[', ']');

      TeXObject name = parser.popNextArg();

      TeXObject version = parser.popNextArg('[', ']');
   }
}