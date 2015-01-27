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
package com.dickimawbooks.texparserlib;

import java.io.IOException;
import java.util.Vector;

public abstract class ControlSequence extends Macro
{
   public ControlSequence(String name)
   {
      this(name, true);
   }

   public ControlSequence(String name, boolean isShort)
   {
      setName(name);
      setShort(isShort);
   }

   public abstract Object clone();

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String toString()
   {
      String name = getName();

      int lastCh = name.charAt(name.length()-1);

      if ((lastCh >= (int)'a' && lastCh <= (int)'z')
         ||lastCh >= (int)'A' && lastCh <= (int)'Z')
      {
         return "\\"+name+" ";
      }

      return "\\"+name;
   }

   public String toString(TeXParser parser)
   {
      return String.format("%c%s", parser.getEscChar(), getName());
   }

   public TeXObjectList string(TeXParser parser)
     throws IOException
   {
      return parser.string(""+parser.getEscChar()+getName());
   }

   public boolean equals(Object obj)
   {
      if (this == obj) return true;

      if (!(obj instanceof ControlSequence) || obj == null) return false;

      ControlSequence other = (ControlSequence)obj;

      return getName().equals(other.getName())
          && isShort() == other.isShort()
          && hasSyntax(other);
   }

   // control sequence name without initial backslash

   protected String name;
}
