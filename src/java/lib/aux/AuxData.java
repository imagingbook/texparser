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
package com.dickimawbooks.texparserlib.aux;

import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;
import java.nio.file.Path;
import java.nio.file.Files;

import com.dickimawbooks.texparserlib.*;
import com.dickimawbooks.texparserlib.generic.*;
import com.dickimawbooks.texparserlib.latex.UnknownReference;

/**
 * Aux data.
 */

public class AuxData
{
   public AuxData(String name, TeXObject[] args)
   {
      this.name = name;
      this.args = args;
   }

   public String getName()
   {
      return name;
   }

   public int getNumArgs()
   {
      return args.length;
   }

   public TeXObject[] getArgs()
   {
      return args;
   }

   public TeXObject getArg(int idx)
   {
      return args[idx];
   }

   public String toString(TeXParser parser)
   {
      StringBuilder builder = new StringBuilder();

      String esc = ""+parser.getEscChar();
      String bg = ""+parser.getBgChar();
      String eg = ""+parser.getEgChar();

      builder.append(esc+name);

      for (int i = 0; i < args.length; i++)
      {
         builder.append(bg+args[i].toString(parser)+eg);
      }

      return builder.toString();
   }

   public String toString()
   {
      StringBuilder builder = new StringBuilder();

      String esc = "\\";
      String bg = "{";
      String eg = "}";

      builder.append(esc+name);

      for (int i = 0; i < args.length; i++)
      {
         builder.append(bg+args[i].toString()+eg);
      }

      return builder.toString();
   }

   public static TeXObject getReference(Vector<AuxData> auxData,
    TeXParser parser, TeXObject object)
    throws IOException
   {
      TeXObjectList expanded = null;

      if (object instanceof Group)
      {
         object = ((Group)object).toList();
      }

      if (object instanceof Expandable)
      {
         expanded = ((Expandable)object).expandfully(parser);
      }

      String label = (expanded == null ?
                      object.toString(parser) : 
                      expanded.toString(parser));

      return getReference(auxData, parser, label);
   }

   public static TeXObject getReference(Vector<AuxData> auxData,
     TeXParser parser, String label)
   throws IOException
   {
      for (AuxData data : auxData)
      {
         if (data.getName().equals("newlabel"))
         {
            TeXObject arg = data.getArg(0);

            if (label.equals(arg.toString(parser)))
            {
               TeXObject params = data.getArg(1);

               if (params instanceof TeXObjectList)
               {
                  TeXObject ref = ((TeXObjectList)params).firstElement();

                  if (ref instanceof Group)
                  {
                     return ((Group)ref).toList();
                  }

                  return ref;
               }

               return params;
            }
         }
      }

      return new UnknownReference(label);
   }

   public static TeXObject getPageReference(Vector<AuxData> auxData,
    TeXParser parser, TeXObject object)
    throws IOException
   {
      TeXObjectList expanded = null;

      if (object instanceof Group)
      {
         object = ((Group)object).toList();
      }

      if (object instanceof Expandable)
      {
         expanded = ((Expandable)object).expandfully(parser);
      }

      String label = (expanded == null ?
                      object.toString(parser) : 
                      expanded.toString(parser));

      return getPageReference(auxData, parser, label);
   }

   public static TeXObject getPageReference(Vector<AuxData> auxData,
     TeXParser parser, String label)
    throws IOException
   {
      for (AuxData data : auxData)
      {
         if (data.getName().equals("newlabel"))
         {
            TeXObject arg = data.getArg(0);

            if (label.equals(arg.toString(parser)))
            {
               TeXObject params = data.getArg(1);

               if (params instanceof TeXObjectList
               && (((TeXObjectList)params).size() > 1))
               {
                  TeXObject ref = ((TeXObjectList)params).get(1);

                  if (ref instanceof Group)
                  {
                     return ((Group)ref).toList();
                  }

                  return ref;
               }

               return params;
            }
         }
      }

      return new UnknownReference(label);
   }

   public static TeXObject getNameReference(Vector<AuxData> auxData,
    TeXParser parser, TeXObject object)
    throws IOException
   {
      TeXObjectList expanded = null;

      if (object instanceof Group)
      {
         object = ((Group)object).toList();
      }

      if (object instanceof Expandable)
      {
         expanded = ((Expandable)object).expandfully(parser);
      }

      String label = (expanded == null ?
                      object.toString(parser) : 
                      expanded.toString(parser));

      return getNameReference(auxData, parser, label);
   }

   public static TeXObject getNameReference(Vector<AuxData> auxData,
     TeXParser parser, String label)
    throws IOException
   {
      for (AuxData data : auxData)
      {
         if (data.getName().equals("newlabel"))
         {
            TeXObject arg = data.getArg(0);

            if (label.equals(arg.toString(parser)))
            {
               TeXObject params = data.getArg(1);

               if (params instanceof TeXObjectList
               && (((TeXObjectList)params).size() > 2))
               {
                  TeXObject ref = ((TeXObjectList)params).get(2);

                  if (ref instanceof Group)
                  {
                     return ((Group)ref).toList();
                  }

                  return ref;
               }

               return params;
            }
         }
      }

      return new UnknownReference(label);
   }

   public static TeXObject getCitation(Vector<AuxData> auxData,
    TeXParser parser, TeXObject object)
    throws IOException
   {
      TeXObjectList expanded = null;

      if (object instanceof Group)
      {
         object = ((Group)object).toList();
      }

      if (object instanceof Expandable)
      {
         expanded = ((Expandable)object).expandfully(parser);
      }

      String label = (expanded == null ?
                      object.toString(parser) : 
                      expanded.toString(parser));

      return getCitation(auxData, parser, label);
   }

   public static TeXObject getCitation(Vector<AuxData> auxData,
     TeXParser parser, String label)
   throws IOException
   {
      for (AuxData data : auxData)
      {
         if (data.getName().equals("bibcite"))
         {
            TeXObject arg = data.getArg(0);

            if (label.equals(arg.toString(parser)))
            {
               return data.getArg(1);
            }
         }
      }

      return new UnknownReference(label);
   }

   private String name;

   private TeXObject[] args;
}
