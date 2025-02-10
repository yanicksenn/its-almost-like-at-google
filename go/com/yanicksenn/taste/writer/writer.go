package writer

import (
	"errors"
	"text/template"
	"os"

	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
)


func Write(file *shared.File, recipe *shared.Recipe, parent string) (error) {
	if file == nil {
		return errors.New("file must not be nil")
	}

	if recipe == nil {
		return errors.New("recipe must not be nil")
	}

	template, err := template.New("t").Parse(recipe.Content)
	if err != nil {
		return errors.New("Failed to parse recipe")
	}

	err = template.Execute(os.Stdout, file)
	if err != nil {
		return errors.New("Failed to build recipe")
	}
	
	return nil
}
