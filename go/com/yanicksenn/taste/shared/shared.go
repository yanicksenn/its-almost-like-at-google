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
	X int32
	Y int32
}

type Token struct {
	Token string
	StartLocation Location
}

