from captcha.image import ImageCaptcha
n="73476"
image = ImageCaptcha()
data = image.generate(str(n))
image.write(str(n), str(n) + '.png')