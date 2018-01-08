package chp6;

/**
 * Created by Jary on 2017/10/14 0014.
 */
public class Class_File {
    private int m;

    public int inc() {
        return m + 1;
    }
}
/*
在vim命令模式下输入
  :%!xxd

     行号    魔数  次版本号 主***  常量池
00000000: cafe babe 0000 0034 0013 0a00 0400 0f09  .......4........
00000010: 0003 0010 0700 1107 0012 0100 016d 0100  .............m..
00000020: 0149 0100 063c 696e 6974 3e01 0003 2829  .I...<init>...()
00000030: 5601 0004 436f 6465 0100 0f4c 696e 654e  V...Code...LineN
00000040: 756d 6265 7254 6162 6c65 0100 0369 6e63  umberTable...inc
00000050: 0100 0328 2949 0100 0a53 6f75 7263 6546  ...()I...SourceF
00000060: 696c 6501 000f 436c 6173 735f 4669 6c65  ile...Class_File
00000070: 2e6a 6176 610c 0007 0008 0c00 0500 0601  .java...........
00000080: 000f 6368 7035 2f43 6c61 7373 5f46 696c  ..chp6/Class_Fil
00000090: 6501 0010 6a61 7661 2f6c 616e 672f 4f62  e...java/lang/Ob
000000a0: 6a65 6374 0021 0003 0004 0000 0001 0002  ject.!..........
000000b0: 0005 0006 0000 0002 0001 0007 0008 0001  ................
000000c0: 0009 0000 001d 0001 0001 0000 0005 2ab7  ..............*.
000000d0: 0001 b100 0000 0100 0a00 0000 0600 0100  ................
000000e0: 0000 0600 0100 0b00 0c00 0100 0900 0000  ................
000000f0: 1f00 0200 0100 0000 072a b400 0204 60ac  .........*....`.
00000100: 0000 0001 000a 0000 0006 0001 0000 000a  ................
00000110: 0001 000d 0000 0002 000e 0a              ........

简单分析：
行号      魔数      次版本号  主*** 常量池       第一个  第二个  3
00000000 cafe babe 0000    0034    0013        0a00   0400  0f09
         能否被JVM   0      52  19:常量池中      10     4   ......
         接受的文件              有1-18个常量



javap -p -v Class_File.class

Classfile /D:/IdeaProjects/JaryNote/common/jvm-book/src/main/java/chp6/Class_File.class
  Last modified 2017-10-14; size 282 bytes
  MD5 checksum 3d146d0ec5ed925459101ef04a5c960b
  Compiled from "Class_File.java"
public class chp6.Class_File
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #4.#15         // java/lang/Object."<init>":()V
   #2 = Fieldref           #3.#16         // chp6/Class_File.m:I
   #3 = Class              #17            // chp6/Class_File
   #4 = Class              #18            // java/lang/Object
   #5 = Utf8               m
   #6 = Utf8               I
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               inc
  #12 = Utf8               ()I
  #13 = Utf8               SourceFile
  #14 = Utf8               Class_File.java
  #15 = NameAndType        #7:#8          // "<init>":()V
  #16 = NameAndType        #5:#6          // m:I
  #17 = Utf8               chp6/Class_File
  #18 = Utf8               java/lang/Object
{
  private int m;
    descriptor: I
    flags: ACC_PRIVATE

  public chp6.Class_File();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 6: 0

  public int inc();
    descriptor: ()I
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: getfield      #2                  // Field m:I
         4: iconst_1
         5: iadd
         6: ireturn
      LineNumberTable:
        line 10: 0
}
SourceFile: "Class_File.java"

 */