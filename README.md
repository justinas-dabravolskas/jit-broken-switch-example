# jit-broken-switch-example
Original discussion in https://github.com/raphw/byte-buddy/issues/1732

ByteBuddy sporadically runs into an "impossible" exception in `net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.visitField`.
This code path can happen just with switch statement skipping all branches including default.
In general this condition is rare, but running `org.example.App with` `-Xcomp` reliably reproduces this condition on `Java 18-23`.


`Exception in thread "main" java.lang.IllegalStateException: Field POOL_NORMAL defines an incompatible default value 0
at net.bytebuddy.dynamic.scaffold.TypeWriter$Default$ValidatingClassVisitor.visitField(TypeWriter.java:2535)
at net.bytebuddy.dynamic.scaffold.TypeWriter$Default$ForInlining$WithFullProcessing$RedefinitionClassVisitor.onVisitField(TypeWriter.java:5164)
at net.bytebuddy.utility.visitor.MetadataAwareClassVisitor.visitField(MetadataAwareClassVisitor.java:278)
at net.bytebuddy.jar.asm.ClassVisitor.visitField(ClassVisitor.java:356)
at net.bytebuddy.jar.asm.commons.ClassRemapper.visitField(ClassRemapper.java:169)
at net.bytebuddy.jar.asm.ClassReader.readField(ClassReader.java:1138)
at net.bytebuddy.jar.asm.ClassReader.accept(ClassReader.java:740)
at net.bytebuddy.utility.AsmClassReader$Default.accept(AsmClassReader.java:132)
at net.bytebuddy.dynamic.scaffold.TypeWriter$Default$ForInlining.create(TypeWriter.java:4039)
at net.bytebuddy.dynamic.scaffold.TypeWriter$Default.make(TypeWriter.java:2246)
at net.bytebuddy.dynamic.DynamicType$Builder$AbstractBase$UsingTypeWriter.make(DynamicType.java:4085)
at net.bytebuddy.dynamic.DynamicType$Builder$AbstractBase.make(DynamicType.java:3769)
at org.example.App.main(App.java:20)`