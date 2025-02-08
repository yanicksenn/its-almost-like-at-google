package shared

type Field struct {
	Name string
	Type string
	IsArray bool
}

type Type struct {
	Name string
	Fields []Field
}

type File struct {
	Namespace string
	Types []Type
}

type Location struct {
	X int
	Y int
}

type Token struct {
	Token string
	Len int
	IsValidIdentifier bool	
	
	// Maybe something like these fields below become useful
	// in the future.
	// IsNumeric bool
	// IsAlphabetic bool
	// IsAlphanumeric bool
	// IsNumber bool
	StartLocation Location
}

