bits 32 
global start 
extern exit, printf, scanf 
import exit msvcrt.dll 
import printf msvcrt.dll 
import scanf msvcrt.dll 

segment data use32 class=data 
format_read db 37, 100, 0 
format_print db 37, 100, 10, 13, 0 
temp dd 0 
x dw 0 
y dw 0 
z dw 0 
temp1 dw 0 
temp2 dw 0 
a dw 0 
temp3 dw 0 
temp4 dw 0 
temp5 dw 0 
temp6 dw 0 
temp7 dw 0 
 

segment code use32 class=code 
start: 
push dword temp 
push dword format_read 
call [scanf] 
add esp, 4 * 2 
mov eax, [temp] 
mov [x], ax 
mov ax, 3 
mov [y], ax 
mov ax, [y] 
mov dx, 2 
imul dx 
mov [temp1], ax 
mov ax, [x] 
add ax, [temp1] 
mov [temp2], ax 
mov ax, [temp2] 
mov [z], ax 
mov eax, 0 
mov ax, [z] 
push eax 
push dword format_print 
call [printf] 
add esp, 4 * 2 
mov ax, [x] 
add ax, [y] 
mov [temp3], ax 
mov ax, [temp3] 
mov [a], ax 
mov eax, 0 
mov ax, [a] 
push eax 
push dword format_print 
call [printf] 
add esp, 4 * 2 
mov ax, [x] 
sub ax, [y] 
mov [temp4], ax 
mov ax, [temp4] 
mov [a], ax 
mov eax, 0 
mov ax, [a] 
push eax 
push dword format_print 
call [printf] 
add esp, 4 * 2 
mov ax, [x] 
mov dx, 0 
mov cx, [y] 
idiv cx 
mov [temp5], ax 
mov ax, [temp5] 
mov [a], ax 
mov eax, 0 
mov ax, [a] 
push eax 
push dword format_print 
call [printf] 
add esp, 4 * 2 
mov ax, [x] 
add ax, [y] 
mov [temp6], ax 
mov ax, [temp6] 
sub ax, [x] 
mov [temp7], ax 
mov ax, [temp7] 
mov [a], ax 
mov eax, 0 
mov ax, [a] 
push eax 
push dword format_print 
call [printf] 
add esp, 4 * 2 
 
push dword 0 
call [exit] 
