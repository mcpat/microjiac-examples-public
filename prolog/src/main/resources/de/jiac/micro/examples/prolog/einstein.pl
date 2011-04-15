#
# MicroJIAC - A Lightweight Agent Framework
# This file is part of Example: Prolog-Example.
#
# Copyright (c) 2007-2011 DAI-Labor, Technische Universität Berlin
#
# This library includes software developed at DAI-Labor, Technische
# Universität Berlin (http://www.dai-labor.de)
#
# This library is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this library.  If not, see <http://www.gnu.org/licenses/>.
#

next_to(X,Y,List) :- iright(X,Y,List).
next_to(X,Y,List) :- iright(Y,X,List).  

einstein(Houses,Fish_Owner) :-
   '='(Houses, [[house,norwegian,_,_,_,_],_,[house,_,_,_,milk,_],_,_]),
   member([house,brit,_,_,_,red],Houses),
   member([house,swede,dog,_,_,_],Houses),
   member([house,dane,_,_,tea,_],Houses),
   iright([house,_,_,_,_,green],[house,_,_,_,_,white],Houses),
   member([house,_,_,_,coffee,green],Houses),
   member([house,_,bird,pallmall,_,_],Houses),
   member([house,_,_,dunhill,_,yellow],Houses),
   next_to([house,_,_,dunhill,_,_],[house,_,horse,_,_,_],Houses),
   member([house,_,_,_,milk,_],Houses),
   next_to([house,_,_,marlboro,_,_],[house,_,cat,_,_,_],Houses),
   next_to([house,_,_,marlboro,_,_],[house,_,_,_,water,_],Houses),
   member([house,_,_,winfield,beer,_],Houses),
   member([house,german,_,rothmans,_,_],Houses),
   next_to([house,norwegian,_,_,_,_],[house,_,_,_,_,blue],Houses),
   member([house,Fish_Owner,fish,_,_,_],Houses).

iright(L,R,[L,R|_]).
iright(L,R,[_|Rest]) :- iright(L,R,Rest).