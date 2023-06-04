from captcha.image import ImageCaptcha
n="20149"
image = ImageCaptcha()
data = image.generate(str(n))
image.write(str(n), str(n) + '.png')