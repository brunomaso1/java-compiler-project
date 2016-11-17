
//  Microsoft (R) .NET Framework IL Disassembler.  Version 4.6.1055.0
//  (c) Microsoft Corporation. Reservados todos los derechos.



// Metadata version: v4.0.30319
.assembly extern mscorlib
{
  .publickeytoken = (B7 7A 5C 56 19 34 E0 89 )                         // .z\V.4..
  .ver 4:0:0:0
}
.assembly easyLanguage
{
  .custom instance void [mscorlib]System.Runtime.CompilerServices.CompilationRelaxationsAttribute::.ctor(int32) = ( 01 00 08 00 00 00 00 00 ) 
  .custom instance void [mscorlib]System.Runtime.CompilerServices.RuntimeCompatibilityAttribute::.ctor() = ( 01 00 01 00 54 02 16 57 72 61 70 4E 6F 6E 45 78   // ....T..WrapNonEx
                                                                                                             63 65 70 74 69 6F 6E 54 68 72 6F 77 73 01 )       // ceptionThrows.

  // --- The following custom attribute is added automatically, do not uncomment -------
  //  .custom instance void [mscorlib]System.Diagnostics.DebuggableAttribute::.ctor(valuetype [mscorlib]System.Diagnostics.DebuggableAttribute/DebuggingModes) = ( 01 00 07 01 00 00 00 00 ) 

  .custom instance void [mscorlib]System.Reflection.AssemblyTitleAttribute::.ctor(string) = ( 01 00 0C 50 72 75 65 62 61 49 6C 64 61 73 6D 00   // ...easyLanguage.
                                                                                              00 ) 
  .custom instance void [mscorlib]System.Reflection.AssemblyDescriptionAttribute::.ctor(string) = ( 01 00 00 00 00 ) 
  .custom instance void [mscorlib]System.Reflection.AssemblyConfigurationAttribute::.ctor(string) = ( 01 00 00 00 00 ) 
  .custom instance void [mscorlib]System.Reflection.AssemblyCompanyAttribute::.ctor(string) = ( 01 00 00 00 00 ) 
  .custom instance void [mscorlib]System.Reflection.AssemblyProductAttribute::.ctor(string) = ( 01 00 0C 50 72 75 65 62 61 49 6C 64 61 73 6D 00   // ...easyLanguage.
                                                                                                00 ) 
  .custom instance void [mscorlib]System.Reflection.AssemblyCopyrightAttribute::.ctor(string) = ( 01 00 12 43 6F 70 79 72 69 67 68 74 20 C2 A9 20   // ...Copyright .. 
                                                                                                  20 32 30 31 36 00 00 )                            //  2016..
  .custom instance void [mscorlib]System.Reflection.AssemblyTrademarkAttribute::.ctor(string) = ( 01 00 00 00 00 ) 
  .custom instance void [mscorlib]System.Runtime.InteropServices.ComVisibleAttribute::.ctor(bool) = ( 01 00 00 00 00 ) 
  .custom instance void [mscorlib]System.Runtime.InteropServices.GuidAttribute::.ctor(string) = ( 01 00 24 38 36 65 39 31 63 66 31 2D 61 64 38 32   // ..$86e91cf1-ad82
                                                                                                  2D 34 34 35 33 2D 61 36 63 61 2D 31 39 37 38 30   // -4453-a6ca-19780
                                                                                                  37 66 66 37 37 39 63 00 00 )                      // 7ff779c..
  .custom instance void [mscorlib]System.Reflection.AssemblyFileVersionAttribute::.ctor(string) = ( 01 00 07 31 2E 30 2E 30 2E 30 00 00 )             // ...1.0.0.0..
  .custom instance void [mscorlib]System.Runtime.Versioning.TargetFrameworkAttribute::.ctor(string) = ( 01 00 1C 2E 4E 45 54 46 72 61 6D 65 77 6F 72 6B   // ....NETFramework
                                                                                                        2C 56 65 72 73 69 6F 6E 3D 76 34 2E 35 2E 32 01   // ,Version=v4.5.2.
                                                                                                        00 54 0E 14 46 72 61 6D 65 77 6F 72 6B 44 69 73   // .T..FrameworkDis
                                                                                                        70 6C 61 79 4E 61 6D 65 14 2E 4E 45 54 20 46 72   // playName..NET Fr
                                                                                                        61 6D 65 77 6F 72 6B 20 34 2E 35 2E 32 )          // amework 4.5.2
  .hash algorithm 0x00008004
  .ver 1:0:0:0
}
.module easyLanguage.exe
// MVID: {1E3A0BB1-A8D0-4428-85E6-AC0E34AAE0AD}
.imagebase 0x00400000
.file alignment 0x00000200
.stackreserve 0x00100000
.subsystem 0x0003       // WINDOWS_CUI
.corflags 0x00020003    //  ILONLY 32BITPREFERRED
// Image base: 0x01150000


// =============== CLASS MEMBERS DECLARATION ===================

.class private auto ansi beforefieldinit easyLanguage.Program
       extends [mscorlib]System.Object
{
  .method private hidebysig static void  Main(string[] args) cil managed
  {
    .entrypoint
    // Code size       39 (0x27)
    .maxstack  3
    .locals init (int32 V_0)

	ldstr "======================================================="
	call       void [mscorlib]System.Console::WriteLine(string)// String(=======================================================)
	nop 
	ldstr "		easyLanguage - Bienvenido."
	call       void [mscorlib]System.Console::WriteLine(string)// String(			easyLanguage - Bienvenido.				   )
	nop 
	ldstr "======================================================="
	call       void [mscorlib]System.Console::WriteLine(string)// String(=======================================================)
	ldstr ""
	call       void [mscorlib]System.Console::WriteLine(string)// String("")
	nop 
	
	
	// variables = []
// maxStack =  3
ldc.i4 0
stloc 0 // $a
ldc.i4 6
call		int32 easyLanguage.Program::fibonacci(int32)
stloc.0
ldloc 0 // $a
call       void [mscorlib]System.Console::Write(int32) // Variable($a)
nop 

			 
    IL_001f:  nop
    IL_0020:  call       valuetype [mscorlib]System.ConsoleKeyInfo [mscorlib]System.Console::ReadKey()
    IL_0025:  pop
    IL_0026:  ret
  } // end of method Program::Main
  
  		.method public hidebysig static int32  fibonacci ( int32 a ) cil managed 
 { 
.maxstack 3 
.locals init (int32 V_0,int32 V_1,int32 V_2) 
IL_0:
nop
ldarg 0 // $a
ldc.i4 1
ceq 
ldarg 0 // $a
ldc.i4 0
ceq 
or 
brfalse.s IL_1
ldarg 0 // $a
stloc 0 // $c
br.s IL_2
IL_1:
ldarg 0 // $a
ldc.i4 1
sub 
call		int32 easyLanguage.Program::fibonacci(int32)
stloc.1
ldarg 0 // $a
ldc.i4 2
sub 
call		int32 easyLanguage.Program::fibonacci(int32)
stloc.2
ldloc 1 // $aux
ldloc 2 // $aux2
add 
stloc 0 // $c
IL_2:nop
br.s IL_3
IL_3: ldloc 0
ret

} // end of method Program::fibonacci 



  .method public hidebysig specialname rtspecialname 
          instance void  .ctor() cil managed
  {
    // Code size       8 (0x8)
    .maxstack  8
    IL_0000:  ldarg.0
    IL_0001:  call       instance void [mscorlib]System.Object::.ctor()
    IL_0006:  nop
    IL_0007:  ret
  } // end of method Program::.ctor

} // end of class easyLanguage.Program


// =============================================================

// *********** DISASSEMBLY COMPLETE ***********************
// ADVERTENCIA: se ha creado el archivo de recursos de Win32 C:\compiladores\easyLanguage.res
