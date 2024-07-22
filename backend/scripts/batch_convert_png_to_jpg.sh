echo $(basename $(pwd))

cd C:/Users/levad/Downloads/"Telegram Desktop"/"notes covers"

echo $(basename $(pwd))

for i in {1..80}
do
	magick ./png/picture"$i".png jpg/"$i".jpg
done

echo "Done"
