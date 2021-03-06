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
package com.dickimawbooks.texparserlib.primitives;

import java.io.IOException;

import com.dickimawbooks.texparserlib.*;

public class IgnoreSpaces extends Primitive
{
   public IgnoreSpaces()
   {
      this("ignorespaces");
   }

   public IgnoreSpaces(String name)
   {
      super(name, true);
   }

   public Object clone()
   {
      return new IgnoreSpaces(getName());
   }

   public void process(TeXParser parser, TeXObjectList stack)
      throws IOException
   {
      TeXObject obj = stack.pop();

      while (obj != null)
      {
         if (obj instanceof Expandable)
         {
            TeXObjectList expanded = ((Expandable)obj).expandfully(parser,
              stack);

            if (expanded != null)
            {
               stack.addAll(0, expanded);
               obj = stack.pop();
            }
         }

         if (!(obj instanceof Ignoreable || obj instanceof Space))
         {
            break;
         }

         obj = stack.pop();
      }

      if (obj != null)
      {
         obj.process(parser, stack);
      }
   }

   public void process(TeXParser parser)
      throws IOException
   {
      TeXObject obj = parser.pop();

      while (obj != null)
      {
         if (obj instanceof Expandable)
         {
            TeXObjectList expanded = ((Expandable)obj).expandfully(parser);

            if (expanded != null)
            {
               parser.addAll(0, expanded);
               obj = parser.pop();
            }
         }

         if (!(obj instanceof Ignoreable || obj instanceof Space))
         {
            break;
         }

         obj = parser.pop();
      }

      if (obj != null)
      {
         obj.process(parser);
      }
   }


}
