import subprocess

output = subprocess.run(
    ["../app/build/install/app/bin/app", "-n=7", "-p1=AlwaysPaper"],
    stdout=subprocess.PIPE,
    text=True,
).stdout

with open("test.expected") as f:
    expected = f.read()

if output != expected:
    print(f"Expected:\n{expected}\nbut got:\n{output}")
    exit(1)
