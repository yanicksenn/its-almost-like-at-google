package main

import (
	"fmt"
	"os"
	"path/filepath"

	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/parser"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/shared"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/tokenizer"
	"github.com/yanicksenn/its-almost-like-at-google/go/com/yanicksenn/taste/writer"
)

func main() {
	cwd, err := os.Getwd()
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(1)
	}

	tasteFile, err := getTasteFileContent(cwd)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(2)
	}
	
	recipeFile, err := getRecipeFileContent(cwd)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(3)
	}

	tokens := tokenizer.Tokenize(tasteFile)
	file, err := parser.Parse(tokens)
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(4)
	}

	recipe := shared.Recipe{
		Content: recipeFile,
	}
	err = writer.Write(file, &recipe, "bogus")
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(4)
	}
}

func getTasteFileContent(cwd string) (string, error) {
	tasteFilePath := filepath.Join(cwd, os.Args[1])
	tasteFileBytes, err := os.ReadFile(tasteFilePath)
	if err != nil {
		return "", nil
	}

	return string(tasteFileBytes), nil
}

func getRecipeFileContent(cwd string) (string, error) {
	recipeFilePath := filepath.Join(cwd, os.Args[2])
	recipeFileBytes, err := os.ReadFile(recipeFilePath)
	if err != nil {
		return "", nil
	}

	return string(recipeFileBytes), nil

}

