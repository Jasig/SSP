
Integer numberOfIds = 0;

println args
if(args.length > 0) {
	numberOfIds = args[0] as Integer
}

for( i in 0..numberOfIds) {
	println UUID.randomUUID()
}