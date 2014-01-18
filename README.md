abbrev is a library of lossless compression/decompression algorithms, 
written in the [Clojure](http://clojure.org) programming language.

Status
------

This library is a hobby project and is neither providing a stable API
nor high-performance implementations of the main lossless algorithms.

Goals
-----

The main goal is to implement most of the published and patent-unencumbered
[lossless compression 
algorithms](http://en.wikipedia.org/wiki/Category:Lossless_compression_algorithms). 

The library should only depend on the core Clojure library, and should
not 'drop down' to Java implementations.

Counterclockwise/Eclipse development
------------------------------------

It is possible to [import the project in Eclipse 
easily](http://tux2323.blogspot.com/2010/08/import-clojure-leiningen-project-into.html), 
using `lein eclipse` and opening the project, provided 
[Counterclockwise](http://code.google.com/p/counterclockwise/) 
is installed.

License
-------

Copyright (C) 2010 Jean-Francois Richard

Distributed under the Eclipse Public License, the same as Clojure uses. 
See the file <tt>COPYING</tt>.
