# Expression-Calculator

## How to contribute

### Add files to eclipse project

First, clone the entire repository.  
`git clone git@github.com:ECE309-Team1/Expression-Calculator.git`  

Be sure you're in the directory that you want your project saved in. (Default is `~/workspace)  

In eclipse, select `file -> new -> project -> Java project`   
Uncheck "use default location", and browse to the folder you cloned this into.

### Create your branch.

Once it's cloned, create a branch for working on whatever deature you're working on.  
Be sure the name is something meaningful. (ie: parser_dev, gui_dev, etc).   
This is to ensure that you can safely work on what you're doing without anyone interfering with your work or yours with there's.   
To create a branch and push it to the remote (aka where it's being hosted on github.com)
Create and checkout the branch locally:   
`git checkout -b <branch name>`   
Push the branch to the remote origin (aka put your changes online)   
`git push origin <branch name>`



Once you've worked on it enough that it's at least stable enough to not ruin anyone elses code, you can merge into the master branch.   
`git checkout master`   
`git merge <branch name>`  

### Write unit tests   

You'll be tempted to test everything you do using short but useless main functions that you'll have to remember to delete before turning it in.   
This takes up a lot of effort. With just a tiny amount of initial time investment, you can learn to write unit tests.

Follow the example in ParsingTester.java. As always, [More info](http://www.vogella.com/tutorials/JUnit/article.html) is everywhere, but testing here should be pretty simple so anything other than the most basic functions aren't really needed.
